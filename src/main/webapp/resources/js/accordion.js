$(document).ready(function () {

    $(".accordion h4:first").addClass("active");
    $(".accordion div:not(:first)").hide();
    $(".accordion h4").click(function () {
        $(this).next("div").slideToggle("slow")
            .siblings("div:visible").slideUp("slow");
        $(this).toggleClass("active");
        $(this).siblings("h4").removeClass("active");
    });

    $('#edit-hotel').submit(function (e) {
        e.preventDefault();
        var $thisForm = $(this);
        if (!$thisForm.valid()) {
            return false;
        }
        var $submit = $thisForm.find(":submit");
        var id = $('input[name=hotelId]').val();
        var myForm = new FormData(this);
        var name = $thisForm.find('input[name=name]').val();
        var stars = $thisForm.find('select[name=stars]').val();
        $submit.attr("disabled", true);
        $('#wait').text("Please wait");
        $.ajax({
            url: '/hotel/' + id + '/edit',
            data: myForm,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                $('#hotel-info').children('div').text("Hotel : " + name);
                $('#image-stars').empty();
                for (var i = 0; i < stars; i++) {
                    $('#image-stars').append('<img class="stars" src="/resources/images/hotels/stars.png"/>');
                }
                $('#hotel-image').attr("src", data);
                $submit.attr("disabled", false);
                $('#wait').text("");
            }
        });
    });

    $('#edit-room').submit(function (e) {
        e.preventDefault();
        var $thisForm = $(this);
        if (!$thisForm.valid()) {
            return false;
        }
        var $submit = $thisForm.find(":submit");
        var roomId = $('input[name=roomId]').val();
        var myForm = new FormData(this);
        var type = $thisForm.find('input[name=type]').val();
        var price = $thisForm.find('select[name=price]').val();
        var description = $thisForm.find('select[name=description]').val();
        var seats = $thisForm.find('select[name=seats]').val();
        $submit.attr("disabled", true);
        $('#wait').text("Please wait");
        $.ajax({
            url: '/room/' + roomId + '/edit',
            data: myForm,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                $('#type').text(data.type);
                $('#price').text(data.price);
                $('#description').text(data.description);
                $('#seats').text(data.seats);
                if (!data.imageLink) {
                    $('#image').html('<img src="/resources/images/rooms/noImage.jpg"/>');
                } else {
                    $('#image').html('<img src="' + data.imageLink + '"/>');
                }
                $submit.attr("disabled", false);
                $('#wait').text("");
            }
        });
    });

    $("#form-booking").submit(function (e) {
        e.preventDefault();
        var userRole;
        $.ajax({
            url: "/get-user",
            type: "GET",
            async: false,
            success: function (responce) {
                userRole = responce;
            }
        });
        if (userRole == "HotelOwner" || userRole == 'CLIENT') {
            $(":submit").attr("disabled", true);
            $.ajax({
                url: "/order/check-date",
                type: "GET",
                data: $("#form-booking").serialize(),
                async: false,
                success: function (data) {
                    if (data) {
                        document.location.href = "/profile";
                    } else {
                        $("#is-free").html("<img class='image-ok' src='/resources/images/error.jpg'/> Sorry, not aviable right now")
                    }
                }
            });
            $(":submit").removeAttr("disabled");
        } else {
            loadPopup();
            return false;
        }
    });
    $("form#add-room").submit(function (e) {
        e.preventDefault();
        var $thisForm = $(this);
        if (!$thisForm.valid()) {
            return false;
        }
        var room = {};
        room.number = $("#roomNumber").val();
        room.type = $("#roomType").val();
        room.description = $("#roomDescription").val();
        room.price = $("#roomPrice").val();
        room.seats = $("#roomPlaces").val();
        room.hotel = {
            id: $("#hotelId").val()
        };
        var d = JSON.stringify(room);
        var $input = $("#imageFile");
        var fd = new FormData;
        if ($input.prop('files')[0]) {
            fd.append('image', $input.prop('files')[0]);
        }
        fd.append('room', d);
        var $submit = $thisForm.find(":submit");
        $submit.attr("disabled", true);
        $('#waitRoom').text("Please wait");
        $.ajax({
            url: "/saveRoom",
            type: "POST",
            processData: false,
            contentType: false,
            data: fd,
            success: function (data) {
                $("#table-rooms").prepend("<tr class=\"room-table\"> <td> <img onerror=\"this.onerror=null;this.src='/resources/images/rooms/noImage.jpg'\"src='" + data.imageLink + "'> </td> <td> <a href='" + data.hotel.id + "/roomDetails/" + data.id + "'>Room № " + data.number + "</a><br/>Type is " + data.type + "<br/>Places: " + data.seats + "<br/>Price: " + data.price + " &#36; </td> </tr>")
                $thisForm[0].reset();
                $submit.attr("disabled", false);
                $('#waitRoom').text("");
            }
        });
    });

});