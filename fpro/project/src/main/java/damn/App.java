package damn;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//here is our main coding space

public class App {
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/restaurant";
    static final String USER = "root";
    static final String PASS = "ncku923923";

    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    static Map<Integer, FoodCon> foodConMap = new HashMap<>();
    static Map<String, Restaurant> restaurantMap = new HashMap<>();
    static Set<String> allType = new HashSet<>();
    static Set<String> mainKind = new HashSet<>();

    //static HashMap<Integer, Restaurant> restaurantMap = new HashMap<>();
    // searching table2
    public static Set<String> findFoodType(Map<Integer, FoodCon> foodmap, Set<String> foodTypeCon) {
        Set<String> result = new HashSet<>();
        
        for (FoodCon food : foodmap.values()) {
            if (foodTypeCon.contains(food.getType())) result.add(food.getFoodName());
        }

        return result;
    }
    public static Set<String> findFoodMainKind(Map<Integer, FoodCon> foodmap, Set<String> mainKindCon) {
        Set<String> result = new HashSet<>();
        
        for (FoodCon food : foodmap.values()) {
            if (mainKindCon.contains(food.getMainKind())) result.add(food.getFoodName());
        }

        return result;
    }

    // searching table1
    public static Map<String, Double> findRestaurants(Map<String, Restaurant> restaurantMap, Set<String> time, Set<String> localCon, Set<String> foodSet) {
        Map<String, Double> result = new TreeMap<>();
        for (Restaurant rest : restaurantMap.values()) {
            if (localCon.contains(rest.getLocal()) && time.contains(rest.getTime())) {
                //System.out.println("hello");
                Set<String> restaurantFoods = new HashSet<>();
                restaurantFoods.add(rest.getFood1());
                restaurantFoods.add(rest.getFood2());
                restaurantFoods.add(rest.getFood3());

                restaurantFoods.retainAll(foodSet);
                //System.out.println(rest.getName() + restaurantFoods);
                if (!restaurantFoods.isEmpty()) {
                    //System.out.println("insert");
                    result.put(rest.getName(), rest.getStar());
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Successfully connected to " + connection.getMetaData().getUserName());

            statement = connection.createStatement();

            // table
            Create createTB = new Create(connection);
            
            /*// remove
            //createTB.removeTable();
            
            // crate
            //createTB.table1();
            //createTB.table2();*/

            // information
            Change isManager = new Change(connection);
            
            /*// insert table1
            // example > isManager.insertRestaurant(id, name, star, food1, food2, food3, local, time)
            //isManager.insertRestaurant(10, "早安美芝城", 3.2, "吐司", "漢堡", "可頌", "東區", "早");
            
            // insert table2
            // example > isManager.insertRestaurant();
            isManager.insertFoodCon(9, "吐司", "西式", "麵包");*/
            
            // save into Map
            // table2
            FoodConRepository foodConRepository = new FoodConRepository(connection);
            foodConMap = foodConRepository.getAllFoodCon();
            allType = foodConRepository.getAllType();
            mainKind = foodConRepository.getmainKind();
            // talbe1
            RestaurantRepository restaurantRepository = new RestaurantRepository(connection);
            restaurantMap = restaurantRepository.getAllRestaurants();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new InterfaceGUIFir(mainKind, allType, foodConMap, restaurantMap).setVisible(true);
                }
            });
            // hand hand hand
            /*// first input
            // type
            System.out.println("第一層");
            Scanner scanner = new Scanner(System.in);

            System.out.print("請輸入要查詢的食物type(空格分隔): ");
            
            String foodType = scanner.nextLine(); //西式 中式 中式 西式
            String[] foodTypes = foodType.split(" ");
            Set<String> foodTypeSet = new HashSet<>();
            
            for (String type : foodTypes) foodTypeSet.add(type);
            //System.out.println(foodTypeSet); // [西式, 中式]
            
            // use a Set to save the result
            Set<String> typeSet = findFoodType(foodConMap, foodTypeSet); // [蛋餅,漢堡,包子,饅頭,刈包,甜甜圈,可頌,貝果,吐司,豆菜麵]

            //System.out.println(typeSet);
            
            // main kind
            System.out.print("請輸入要查詢的食物mainKind(空格分隔): ");

            String mainKind = scanner.nextLine(); // 麵 餅 麵
            String[] mainKinds = mainKind.split(" ");
            Set<String> mainKindCon = new HashSet<>();

            for (String kind : mainKinds) mainKindCon.add(kind); // [麵, 餅]
            //System.out.println(mainKindCon);
            // use a Set to save the result
            Set<String> mainKindSet = findFoodMainKind(foodConMap, mainKindCon); // [豆菜麵, 蛋餅]
            //System.out.println(mainKindSet);

            // Union foodSet
            mainKindSet.retainAll(typeSet);
            if (mainKindSet.isEmpty()) {
                System.out.println("查無菜名");
                return;
            }  else {
                System.out.println(mainKindSet);
            }
            
            // second input
            System.out.println("第二層");
            System.out.print("請輸入要查詢的地區(空格分隔): ");
            
            // location
            String local = scanner.nextLine();
            String[] locals = local.split(" ");
            Set<String> localCon = new HashSet<>();
            for (String loc : locals) localCon.add(loc);
            //System.out.println(localCon);
            Set<String> localSet = findRestaurantLocalSet(restaurantMap, localCon);
            System.out.println(localSet);

            // time
            System.out.print("請輸入用餐時間(早/午晚/宵夜): ");

            String time = scanner.nextLine();
            String[] times = time.split(" ");
            Set<String> timeSet = new HashSet<>();
            for (String t : times) timeSet.add(t);
            //Set<String> timeSet = findRestaurantTimeSet(restaurantMap, time);
            //System.out.println(timeSet);

            //Union: time location'
            //timeSet.retainAll(localSet);
            //System.out.println(timeSet);
            // unsorted
            Map<String, Double> restaurant = findRestaurants(restaurantMap, timeSet, localCon, mainKindSet);
            //System.out.println(restaurant);
            
            //sorted 
            List<Map.Entry<String, Double>> entryList = new ArrayList<>(restaurant.entrySet());

            entryList.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

            Map<String, Double> sortedRestaurant = new LinkedHashMap<>();
            for (Map.Entry<String, Double> entry : entryList) {
                sortedRestaurant.put(entry.getKey(), entry.getValue());
            }

            for (String name : sortedRestaurant.keySet()) {
                System.out.println(name);
            }*/
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
// GUI
class InterfaceGUIFir extends JFrame {
    private Map<Integer, FoodCon> foodConMap;
    private Map<String, Restaurant> restaurantMap;
    private Set<String> allType;
    private Set<String> mainKind;

    private JPanel mainPanel;
    private JPanel typePanel;
    private JPanel kindPanel;

    private JButton searchButton;

    public InterfaceGUIFir(Set<String> mainKind, Set<String> allType, Map<Integer, FoodCon> foodConMap, Map<String, Restaurant> restaurantMap) {
        // set
        this.allType = allType;
        this.mainKind = mainKind;
        this.foodConMap = foodConMap;
        this.restaurantMap = restaurantMap;
        
        // window_title
        setTitle("Restaurant Searching");

        // window_size
        setSize(300, 450);

        // window sit
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        // exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // type
        typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (String type : allType) {
            JCheckBox checkBox = new JCheckBox(type);
            typePanel.add(checkBox);
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(typePanel, gbc);

        // mainkind
        kindPanel = new JPanel();
        kindPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (String kind : mainKind) {
            JCheckBox checkBox = new JCheckBox(kind);
            kindPanel.add(checkBox);
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(kindPanel, gbc);

        // next bottom
        searchButton = new JButton("next");
        searchButton.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(searchButton, gbc);
        
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchButtonClick();
            }
        });

        // window display
        add(mainPanel);
        setVisible(true);
    }
    private void SearchButtonClick() {
        //System.out.println(foodConMap);
        //System.out.println(restaurantMap);
        
        // type click
        Component[] typeComponents = typePanel.getComponents();
        Set<String> foodTypeCon = new HashSet<>();
        for (Component component : typeComponents) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    foodTypeCon.add(checkBox.getText());
                }
            }
        }

        // kind click
        Component[] kindComponents = kindPanel.getComponents();
        Set<String> mainKindCon= new HashSet<>();
        for (Component component : kindComponents) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    mainKindCon.add(checkBox.getText());
                }
            }
        }

        // Searching
        Set<String> foodTypeSet = App.findFoodType(foodConMap, foodTypeCon);
        Set<String> mainKindSet = App.findFoodMainKind(foodConMap, mainKindCon);
        mainKindSet.retainAll(foodTypeSet);
        
        // Union
        Set<String> foodSet = new HashSet<>();
        foodSet.addAll(mainKindSet);

        // special case : no food
        if (foodSet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "查無菜名", "hint", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else {
            InterfaceGUISecond guiSecond = new InterfaceGUISecond(foodSet, restaurantMap);
            guiSecond.setVisible(true);
            this.dispose();
        }
        /*// new interface
        InterfaceGUISecond guiSecond = new InterfaceGUISecond(foodSet, restaurantMap);
        guiSecond.setVisible(true);
        this.dispose();*/
    }
}
class InterfaceGUISecond extends JFrame {
    private Set<String> foodSet;
    private Map<String, Restaurant> restaurantMap;

    private JPanel foodPanel;
    private JPanel mainPanel;
    private JCheckBox northCheckBox, southCheckBox, eastCheckBox, westCheckBox;
    private JCheckBox morningCheckBox, afternoonCheckBox, sleepCheckBox;
    private JButton searchButton;

    public InterfaceGUISecond(Set<String> foodSet, Map<String, Restaurant> restaurantMap) {
        this.foodSet = foodSet;
        this.restaurantMap = restaurantMap;

        // set window
        setTitle("Food Searching Result");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // set local
        JPanel localPanel = new JPanel();
        localPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        northCheckBox = new JCheckBox("北區");
        southCheckBox = new JCheckBox("南區");
        eastCheckBox = new JCheckBox("東區");
        westCheckBox = new JCheckBox("中西區");
        localPanel.add(northCheckBox);
        localPanel.add(southCheckBox);
        localPanel.add(eastCheckBox);
        localPanel.add(westCheckBox);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(localPanel, gbc);

        // set time
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        morningCheckBox = new JCheckBox("早");
        afternoonCheckBox = new JCheckBox("午晚");
        sleepCheckBox = new JCheckBox("宵夜");
        timePanel.add(morningCheckBox);
        timePanel.add(afternoonCheckBox);
        timePanel.add(sleepCheckBox);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(timePanel, gbc);
        
        // set food
        foodPanel = new JPanel();
        foodPanel.setLayout(new GridLayout(0,2,10,10));
        for (String food : foodSet) {
            JCheckBox checkBox = new JCheckBox(food);
            foodPanel.add(checkBox);
        }
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(foodPanel, gbc);

        // Searching Buttom
        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(searchButton, gbc);

        // touch search bottom
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchButtonClick();
            }
        });

        // 將主面板添加到視窗
        add(mainPanel);
    }
    // echo method
    private void SearchButtonClick() {
        

        Set<String> localCon = new HashSet<>();
        if (northCheckBox.isSelected()) localCon.add("北區");
        if (southCheckBox.isSelected()) localCon.add("南區");
        if (eastCheckBox.isSelected()) localCon.add("東區");
        if (westCheckBox.isSelected()) localCon.add("中西區");

        Set<String> timeCon = new HashSet<>();
        if (morningCheckBox.isSelected()) timeCon.add("早");
        if (afternoonCheckBox.isSelected()) timeCon.add("午晚");
        if (sleepCheckBox.isSelected()) timeCon.add("宵夜");

        Component[] components = foodPanel.getComponents();
        Set<String> selectedFoods = new HashSet<>();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    selectedFoods.add(checkBox.getText());
                }
            }
        }
        // find restaurant
        Map<String, Double> restaurant = App.findRestaurants(restaurantMap, timeCon, localCon, selectedFoods);
        if (restaurant.isEmpty()) {
            JOptionPane.showMessageDialog(this, "查無店家", "hint", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        // sorted
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(restaurant.entrySet());

        entryList.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

        Map<String, Double> sortedRestaurant = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : entryList) {
            sortedRestaurant.put(entry.getKey(), entry.getValue());
        }
        // next interface
        InterfaceGUIThird guiThird = new InterfaceGUIThird(sortedRestaurant, restaurantMap);
        guiThird.setVisible(true);
        this.dispose();
    }
}
class InterfaceGUIThird  extends JFrame{
    private Map<String, Double> sortedRestaurant;
    private Map<String, Restaurant> restaurantMap;
    private JPanel mainPanel;
    
    public InterfaceGUIThird(Map<String, Double> sortedRestaurant, Map<String, Restaurant> restaurantMap) {
        this.sortedRestaurant = sortedRestaurant;
        this.restaurantMap = restaurantMap;
        System.out.println(restaurantMap);
        // set window
        setTitle("Restaurant Searching Result");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Create table model and table
        String[] columnNames = {"Restaurant", "Star", "Time", "local"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (Map.Entry<String, Double> entry : sortedRestaurant.entrySet()) {
            String restaurantName = entry.getKey();
            double star = entry.getValue();
            
            Restaurant info = restaurantMap.get(restaurantName);
            String time = info.getTime();
            String local = info.getLocal();
            Object[] rowData = {restaurantName, star, time, local};
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the table to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(scrollPane, gbc);

        add(mainPanel);
        setVisible(true);
    }
}