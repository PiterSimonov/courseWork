$(document).ready(function () {
    $("#hotelSubmit").click(function () {

        var conveniences = [];
        $.each($("#conveniences :input:checked"), function () {
            var convenience = {
                id: this.value
            };
            conveniences.push(convenience)
        });

        var hotel = {};
        hotel.name = $("#hotelName").val();
        hotel.stars = $("#stars").val();
        hotel.city = {
            id: $("#city_id").val()
        };
        hotel.conveniences = conveniences;

        var d = JSON.stringify(hotel);
        var $input = $("#image");
        var fd = new FormData;

        fd.append('image', $input.prop('files')[0]);
        fd.append('hotel', d);

        $.ajax({
            url: "/search/saveHotel",
            type: "POST",
            processData: false,
            contentType: false,
            data: fd,
            success: function (data) {
                $("#table-hotels").prepend("<tr><td><div><img  onerror=\"this.onerror=null;this.src='../resources/images/rooms/noImage.jpg'\" src='" + data.imageLink + "'></div></td><td><div><a href='/hotel/" + data.id + "'>" + data.name + "</a> in " + data.city.name + " </div></td></tr>");
            }
        });

    })
});
