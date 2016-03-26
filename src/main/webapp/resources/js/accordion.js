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
        var $submit = $thisForm.find(":submit");
        var id = $('input[name=hotelId]').val();
        var myForm = new FormData(this);
        var name = $thisForm.find('input[name=name]').val();
        var stars = $thisForm.find('select[name=stars]').val();
        if($thisForm.valid()){
            $submit.attr("disabled", true);
            $('#wait').text("Please wait");
        }
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
                for (var i =0; i<stars; i++){
                    $('#image-stars').append('<img class="stars" src="/resources/images/hotels/stars.png"/>');
                }
                $submit.attr("disabled", false);
                $('#wait').text("");
            }
        });
    });

    $('#edit-room').submit(function (e) {
        e.preventDefault();
        var $thisForm = $(this);
        var $submit = $thisForm.find(":submit");
        var roomId = $('input[name=roomId]').val();
        var myForm = new FormData(this);
        var type = $thisForm.find('input[name=type]').val();
        var price = $thisForm.find('select[name=price]').val();
        var description = $thisForm.find('select[name=description]').val();
        var seats = $thisForm.find('select[name=seats]').val();
        $.ajax({
            url: '/room/' + roomId + '/edit',
            data: myForm,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            beforeSend: function () {
                $submit.attr("disabled", true);
                $('#wait').text("Please wait");
            },
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


});