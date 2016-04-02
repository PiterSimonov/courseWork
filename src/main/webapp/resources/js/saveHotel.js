$(document).ready(function () {
    $("#add-hotel").submit(function (e) {
        e.preventDefault();
        var $thisForm = $(this);
        if (!$thisForm.valid()) {
            return false;
        }
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
        var $submit = $thisForm.find(":submit");
        $submit.attr("disabled", true);
        $('#wait').text("Please wait");
        if ($input.prop('files')[0]) {
            fd.append('image', $input.prop('files')[0]);
        }
        fd.append('hotel', d);

        $.ajax({
            url: "/saveHotel",
            type: "POST",
            processData: false,
            contentType: false,
            data: fd,
            success: function (data) {
                $("#table-hotels").prepend("<tr><td><div><img class=\"hotel-img\" onerror=\"this.onerror=null;this.src='../resources/images/rooms/noImage.jpg'\" src='" + data.imageLink + "'></div></td><td><div><a href='/hotel/" + data.id + "'>" + data.name + "</a> in " + data.city.name + " </div></td></tr>");
                $thisForm[0].reset();
                $submit.attr("disabled", false);
                $('#wait').text("");
            }
        });

    });

    $('#country_id').on("change", function () {
        $('#city_id').removeAttr("disabled");
        var data = $('#country_id').val();
        $.get('/search/city', {country_id: data}, function (result) {
            var option = '';
            $.each(result, function (key, data) {
                option += '<option value="' + key + '">' + data + '</option>'
            });
            $('#city_id').html(option);
        })
    });
});
