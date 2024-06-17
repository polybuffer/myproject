$(function(){
    //獲取飯店列表
    getRestaurantList();
});

//獲取飯店列表
function getRestaurantList(){
    $.ajax({
        url:"/RestaurantRest/getRestaurantList",
        data:{
            foodName:$("#foodName").val(),
            foodArea:$("#foodArea").val(),
            foodTime:$("#foodTime").val()
        },
        async:false,
        dataType:"json",
        success:function (restaurantList) {
             $("#restaurantList").empty();
             for(var i=0;i<restaurantList.length;i++){
                 var restaurant = restaurantList[i];
                 $("#restaurantList").append(
                     "<div class=\"col-md-4\">\n" +
                     "\t\t\t<div class=\"restaurantClass\">\n" +
                     "\t\t\t\t<h1>"+restaurant.name+"</h1>\n" +
                     "\t\t\t\t<p>評分："+restaurant.star+"</p>\n" +
                     "\t\t\t</div>\n" +
                     "\t\t</div>"
                 )
             }

        }
    });
}


