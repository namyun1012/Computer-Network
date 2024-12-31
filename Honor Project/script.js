$("#get").click(function() {
    alert("button clicked!");

    $.ajax({
        type: "GET",
        url: "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=0a36a516f1bec217908054fbdefcb3f7",
        data : {},
        success : function (response) {
            let temperature = (response["main"]["temp"] - 273.15); 
            let temperatureMax = response["main"]["temp_max"] - 273.15;
            let temperatureMin =response["main"]["temp_min"] - 273.15;
            let temp_range = temperatureMax - temperatureMin
            
            console.log("현재 온도:", temperature);
            console.log("최고 온도:", temperatureMax);
            console.log("최저 온도:", temperatureMin);
            console.log("일교차:", temp_range);

        },


        error : function(err) {
            alert("an error");
        }
    })
});


