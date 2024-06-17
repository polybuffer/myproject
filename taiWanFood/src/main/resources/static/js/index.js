$(function(){
	$("#searchBtn").click(function(){
		window.location.href="/restaurant?foodName="+$("#foodName").val()+"&foodArea="+$("#areaSelect").val()+"&foodTime="+$("#timeSelect").val();
	})
	$("#foodType").change(function(){
		getFoodName($("#foodType").val(),$("#foodCategory").val())
	})
	$("#foodCategory").change(function(){
		getFoodName($("#foodType").val(),$("#foodCategory").val())
	})


	//獲取品種
	getFoodCategory();

	//獲取菜式
	getFoodType();

})

//獲取菜式
function getFoodType(){
	$.ajax({
		url:"/IndexRest/getFoodType",
		data:{
		},
		async:false,
		dataType:"json",
		success:function (foodTypeList) {
			$("#foodType").empty();
			$("#foodType").empty();
			$("#foodType").append(
				"<option>請選擇</option>"
			)
			for(var i=0;i<foodTypeList.length;i++){
				var foodType = foodTypeList[i];
				$("#foodType").append(
					"<option>"+foodType+"</option>"
				)
			}
		}
	});
}

//獲取品種
function getFoodCategory(){
	$.ajax({
		url:"/IndexRest/getFoodCategory",
		data:{

		},
		async:false,
		dataType:"json",
		success:function (foodCategoryList) {
			$("#foodCategory").empty();
			$("#foodCategory").empty();
			$("#foodCategory").append(
				"<option>請選擇</option>"
			)
			for(var i=0;i<foodCategoryList.length;i++){
				var foodCategory = foodCategoryList[i];
				$("#foodCategory").append(
					"<option>"+foodCategory+"</option>"
				)
			}

		}
	});
}

//獲取菜名
function getFoodName(foodType,foodCategory){
	$.ajax({
		url:"/IndexRest/getFoodName",
		data:{
			foodType:foodType,
			foodCategory:foodCategory
		},
		async:false,
		dataType:"json",
		success:function (foodNameList) {
			$("#foodName").empty();
			$("#foodName").empty();
			$("#foodName").append(
				"<option>請選擇</option>"
			)
			for(var i=0;i<foodNameList.length;i++){
				var foodName = foodNameList[i];
				$("#foodName").append(
					"<option>"+foodName+"</option>"
				)
			}


		},
		error: function() {
			alert("Error fetching food names");
		}
	});
}