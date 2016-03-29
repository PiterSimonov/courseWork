$(document).ready(function () {
    $("#roomSubmit").click(function () {

        var room = {};
        room.number = $("#roomNumber").val();
        room.type = $("#roomType").val();
        room.description = $("#roomDescription").val();
        room.price = $("#roomPrice").val();
        room.seats = $("#roomPlaces").val();
        room.hotel = {
            id: $("#hotelId").val()
        }

        var d = JSON.stringify(room);
        var $input = $("#imageFile");
        var fd = new FormData;

        fd.append('image', $input.prop('files')[0]);
        fd.append('room', d);

        $.ajax({
            url: "/search/saveRoom",
            type: "POST",
            processData: false,
            contentType: false,
            data: fd,
            success: function (data) {
                $("#table-rooms").prepend("<tr class=\"room-table\"> <td> <img onerror=\"this.onerror=null;this.src='/resources/images/rooms/noImage.jpg'\"src='" + data.imageLink + "'> </td> <td> <a href='" + data.hotel.id + "/roomDetails/" + data.id + "'>Room № " + data.number + "</a><br/>Type is " + data.type + "<br/>Places: " + data.seats + "<br/>Price: " + data.price + " &#36; </td> </tr>")
            }
        });

    })
});
