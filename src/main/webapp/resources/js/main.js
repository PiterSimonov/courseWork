$(document).ready(function () {

    var d = new Date();
    var today = dateToString(d);
    var nextDay = dateToString(d, 1);
    var maxDays = dateToString(d, 28);
    var $fromDate = $('#fromDate');
    var $toDate = $('#toDate');
    $fromDate.attr("value", today);
    $fromDate.attr("min", today);
    $toDate.attr("value", nextDay);
    $toDate.attr("min", nextDay);
    $toDate.attr("max", maxDays);

    var $bookingDate =$('.booking-date');
    $bookingDate.attr("min", today);
    $bookingDate.attr("value", today);


    $fromDate.change(function () {
        $("#fromDate").attr("value", fromDate.value);
        var x = fromDate.value;
        var d = new Date(Date.parse(x));
        var nextDay = dateToString(d, 1);
        var maxDays = dateToString(d, 28);
        var $toDate = $("#toDate");
        var $endDate =$("#endDate");
        $toDate.attr("max", maxDays);
        $toDate.attr("value", nextDay);
        $toDate.val(nextDay);
        $toDate.attr("min", nextDay);
        $endDate.attr("min",x);
        $endDate.attr("value",x);
        $endDate.val(x);
    });

    function dateToString(d, num) {
        if (num === undefined) {
            return d.toJSON().split('T')[0];
        } else {
            var nextDay = new Date(d.valueOf() + 24 * 60 * 60 * 1000 * num);
            return nextDay.toJSON().split('T')[0];
        }
    }

    $('#country_id').one('click', function () {
        $.get('/search/country', function (result) {
            var option = '';
            $.each(result, function (key, data) {
                option += '<option value="' + key + '">' + data + '</option>'
            });
            $('#country_id').html(option);
        })
    }).on("change", function () {
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

    $('.delete-order').on('click', function(e){
        e.preventDefault();
        var $thisOrder = $(this);
        var $divParent = $thisOrder.parent("div");
        var orderId = $(this).attr('href');
        $.ajax({
            url: "/order/"+orderId+"/delete",
            type: 'POST',
            success: function(date){
                if (date){
                    $divParent.remove();
                } else {
                    document.location.href = "/error?message=Your order has been deleted"
                }
            }
        });
    });
});