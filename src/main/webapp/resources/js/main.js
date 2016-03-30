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

    var $bookingDate = $('.booking-date');
    $bookingDate.attr("min", today);
    $bookingDate.attr("value", today);


    $fromDate.change(function () {
        $("#fromDate").attr("value", fromDate.value);
        var x = fromDate.value;
        var d = new Date(Date.parse(x));
        var nextDay = dateToString(d, 1);
        var maxDays = dateToString(d, 28);
        var $toDate = $("#toDate");
        var $endDate = $("#endDate");
        $toDate.attr("max", maxDays);
        $toDate.attr("value", nextDay);
        $toDate.val(nextDay);
        $toDate.attr("min", nextDay);
        $endDate.attr("min", x);
        $endDate.attr("value", x);
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

    $("#city").keyup(function () {
        city.setAttribute("value", city.value);
        var $city = $("#cityList");
        $city.html("");
        $city.css("display", "none");
        if (city.value.length >= 3) {
            var countryId = $("#countryId").attr("value");
            $.ajax({
                url: "/search/city/" + city.value + "/" + countryId,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    data.forEach(function (i) {
                        var li = li = document.createElement('li');
                        li.appendChild(document.createTextNode(i.name));
                        li.onclick = function () {
                            $("#city").attr("value", i.name);
                            $("#city").val(i.name);
                            $("#cityId").attr("value", i.id);
                            $("#hotel").attr("value", "");
                            $("#hotelId").attr("value", 0);
                            $("#hotelList").html("");
                            $city.html("");
                            $city.css("display", "none");
                        };
                        $city.append(li);
                        $city.css("display", "block");
                    })
                }
            });
        }
    });

    $("#country").keyup(function () {
        country.setAttribute("value", country.value);
        var $country = $("#countryList");
        $country.html("");
        $country.css("display", "none");

        if (country.value.length >= 3) {
            $.ajax({
                url: "/search/country/" + country.value,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    data.forEach(function (i) {
                        var li = li = document.createElement('li');
                        li.appendChild(document.createTextNode(i.name));
                        li.onclick = function () {
                            $("#country").attr("value", i.name);
                            $("#country").val(i.name);
                            $("#countryId").attr("value", i.id);
                            $country.html("");
                            $("#city").attr("value", "");
                            $("#city").val("");
                            $("#cityId").attr("value", 0);
                            $("#cityList").html("");
                            $("#hotel").attr("value", "");
                            $("#hotel").val("");
                            $("#hotelId").attr("value", 0);
                            $("#hotelList").html("");
                            $country.css("display", "none");
                        };
                        $country.append(li);
                        $country.css("display", "block");
                    })
                }
            });
        }
    });

    $("#hotel").keyup(function () {
        hotel.setAttribute("value", hotel.value);
        var $hotel = $("#hotelList");
        $hotel.html("");
        $hotel.css("display", "none");
        if (hotel.value.length >= 3) {
            var cityId = $("#cityId").attr("value");
            var countryId = $("#countryId").attr("value");
            $.ajax({
                url: "/search/hotel/" + hotel.value + "/" + cityId + "/" + countryId,
                type: "GET",
                dataType: "json",
                success: function (data) {

                    data.forEach(function (i) {
                        var li = li = document.createElement('li');
                        li.appendChild(document.createTextNode(i.name));
                        li.onclick = function () {
                            $("#hotel").val(i.name);
                            $("#hotel").attr("value", i.name);
                            $("#hotelId").attr("value", i.id);

                            $hotel.html("");
                            $hotel.css("display", "none");
                        };
                        $hotel.append(li);
                        $hotel.css("display", "block");
                    })
                }
            });
        }
    });

    $("#country").change(function () {
        $("#countryId").attr("value", 0);
    });

    $("#city").change(function () {
        $("#cityId").attr("value", 0);
    });

    $("#hotel").change(function () {
        $("#hotelId").attr("value", 0);
    });
    var roomCounter = 1;
    $("#addRoom").click(function () {

        var number = document.createElement("input");
        number.type = "number";
        number.min = 1;
        number.setAttribute("value", 1);
        number.max = 8;
        number.onchange = function () {
            number.setAttribute("value", number.value);
        };

        var del = document.createElement("input");
        del.type = "button";
        del.value = "X";
        del.style = "width:24px;height:22px;color:red";
        del.onclick = function () {
            del.parentElement.removeChild(del);
            number.parentElement.removeChild(number);
            roomCounter--;
            $("#addRoom").attr("disabled", false);
        };
        number.appendChild(del);
        $("#rooms").append(number);
        $("#rooms").append(del);

        roomCounter++;
        if (roomCounter >= 8) {
            $("#addRoom").attr("disabled", true);
        }
    });

    $("body").click(function (e) {
        if (e.target.id !== "countryList" && e.target.id !== "country") {
            $("#countryList").css("display", "none");
        }
        if (e.target.id !== "cityList" && e.target.id !== "city") {
            $("#cityList").css("display", "none");
        }
        if (e.target.id !== "hotelList" && e.target.id !== "hotel") {
            $("#hotelList").css("display", "none");
        }
    });

    $("#country").focus(function () {
        if ($("#countryList").children().length > 0) {
            $("#countryList").css("display", "block");
        }
    });

    $("#city").focus(function () {
        if ($("#cityList").children().length > 0) {
            $("#cityList").css("display", "block");
        }
    });

    $("#hotel").focus(function () {
        if ($("#hotelList").children().length > 0) {
            $("#hotelList").css("display", "block");
        }
    });

    $("#search").click(function () {
        if ($("#countryId").val() == 0 && $("#cityId").val() == 0 && $("#hotelId").val() == 0) {
            alert("Input Country, City or Hotel");
            $("#country").focus();
        } else {
            var form = $('#left-panel').html();
            localStorage.setItem("form", form);
            var seats = {};
            var elements = $("#rooms :input[type='number']");
            $.each(elements, function () {
                var x = this.value;
                if (x in seats) {
                    var val = seats[this.value];
                    seats[this.value] = val + 1;

                } else {
                    seats[this.value] = 1;
                }
            });

            var param = {};
            param.countryId = $("#countryId").val();
            param.cityId = $("#cityId").val();
            param.hotelId = $("#hotelId").val();
            param.startDate = $("#fromDate").val();
            param.endDate = $("#toDate").val();
            param.seats = seats;
            param.firstResult = 0;
            param.limit = 5;

            var jsonData = JSON.stringify(param);
            $.ajax({
                url: "/search/test",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: jsonData,
                success: function () {
                    document.location.href = "/search/hotels"
                }
            });
        }
    });

    var firstResult = 0;

    $("#more").on("click", (function () {
        firstResult = firstResult + 5;
        $.ajax({
            url: "/search/nextHotels/" + firstResult,
            type: "GET",
            success: function (data) {
                $("#hotelsList").append(data);
            }
        });
    }));

    $("#numOfTravelers").on("change", (function () {
        numOfTravelers.setAttribute("value", numOfTravelers.value);
    }));

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

    $('#add-hotel').submit(function (e) {
        var $thisForm = $(this);
        var $submit = $thisForm.find(":submit");
        if ($thisForm.valid()) {
            $submit.attr("disabled", true);
            $('#wait').text("Please wait");
        }
        return true;
    });

    $('.delete-order').on('click', function (e) {
        e.preventDefault();
        var $thisOrder = $(this);
        var $divParent = $thisOrder.parent("div");
        var orderId = $(this).attr('href');
        $.ajax({
            url: "/order/" + orderId + "/delete",
            type: 'POST',
            success: function (date) {
                if (date) {
                    $divParent.remove();
                } else {
                    document.location.href = "/error?message=Your order has been deleted"
                }
            }
        });
    });
});