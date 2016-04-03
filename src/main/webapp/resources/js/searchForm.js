$(document).ready(function () {

    $("#fromDate").change(function () {
        $("#fromDate").attr("value", fromDate.value);
        var x = fromDate.value;
        var d = new Date(Date.parse(x));
        var nextDay = dateToString(d, 1);
        var maxDays = dateToString(d, 28);
        var $toDate = $("#toDate");
        $toDate.attr("max", maxDays);
        $toDate.attr("value", nextDay);
        $toDate.val(nextDay);
        $toDate.attr("min", nextDay);
    });

    $("#toDate").change(function () {
        $("#toDate").attr("value", toDate.value);
        var x = toDate.value;
        var d = new Date(Date.parse(x));
        var $toDate = $("#toDate");
        $toDate.attr("value", dateToString(d));
        $toDate.val(dateToString(d));
    });


    function dateToString(d, num) {
        if (num === undefined) {
            return d.toJSON().split('T')[0];
        } else {
            var nextDay = new Date(d.valueOf() + 24 * 60 * 60 * 1000 * num);
            return nextDay.toJSON().split('T')[0];
        }
    };

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

    var roomCounter = $("#rooms :input[type='number']").length;

    $("#addRoom").on("click", (function () {

        var number = document.createElement("input");
        number.type = "number";
        number.min = 1;
        number.setAttribute("value", 2);
        number.max = 8;
        number.onchange = function () {
            number.setAttribute("value", number.value);
        };

        var del = document.createElement("input");
        del.type = "button";
        del.value = "X";
        del.style = "width:24px;height:22px;color:red";

        number.appendChild(del);
        $("#rooms").append(number);
        $("#rooms").append(del);

        roomCounter++;
        if (roomCounter >= 8) {
            $("#addRoom").attr("disabled", true);
        }
    }));

    $('body').on('click', "#rooms :input[type='button']", function () {

        var el = this.previousElementSibling;
        this.parentElement.removeChild(el);
        this.parentElement.removeChild(this);
        roomCounter--;
        $("#addRoom").attr("disabled", false);
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
            var stars = $("#stars").val();
            localStorage.setItem("form", form);
            localStorage.setItem("stars", stars);  // IT'S NEED FOR STARS FILTERING !!!!
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
            param.stars= stars;       // IT'S NEED FOR STARS FILTERING !!!!
            param.firstResult = 0;
            param.limit = 5;
            history.pushState(param, "Searching Hotels", "/search/hotels")
            var jsonData = JSON.stringify(param);
            $.ajax({
                url: "/search/hotels",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: jsonData,
                success: function (data) {
                    $('#hotelsList').html(data);
                    $('#more-div').html('<input type="button" name="more" id="more" value="More">');
                }
            });
        }
    });
    $(window).on('popstate', function () {
        $.ajax({
            url: location.pathname, success: function (data) {
                $('body').html(data);
            }
        });
    });

    var firstResult = 0;
    var inProgress = false;
    $("body").on("click", '#more', function () {
        var button = $(this);
        button.attr("disabled", true);
        if (!inProgress) {
            firstResult = firstResult + 5;
            $.ajax({
                url: "/search/nextHotels/" + firstResult,
                type: "GET",
                beforeSend: function () {
                    inProgress = true;
                },
                success: function (data) {
                    if (data.length > 4) {
                        $("#hotelsList").append(data);
                        inProgress = false;
                        button.attr("disabled", false);
                    }
                }
            });
        }
    });

    $("#numOfTravelers").on("change", (function () {
        numOfTravelers.setAttribute("value", numOfTravelers.value);
    }));
});
