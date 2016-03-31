<div class="left-panel" id="left-panel">
    <div class="filter">
        <span><h5>Filtering Room</h5></span>
        <form id="filter-form" action="search" autocomplete="off">
            <label for="country">Country</label>
            <div id="first" class="AutoComplete">
                <input id="country" type="text" name="country" value="" autocomplete="off">
                <ul id="countryList"></ul>
                <input id="countryId" name="countryId" value="0" type="number" hidden>
            </div>

            <label for="city">City</label>
            <div class="AutoComplete">
                <input id="city" type="text" name="city" autocomplete="off">
                <ul id="cityList"></ul>
                <input id="cityId" name="cityId" value="0" type="number" hidden>
            </div>

            <label for="hotel">Hotel</label>
            <div class="AutoComplete">
                <input id="hotel" type="text" name="hotel" autocomplete="off">
                <ul id="hotelList"></ul>
                <input id="hotelId" name="hotelId" value="0" type="number" hidden>
            </div>

            <label>Hotel Rank</label>
            <select form="filter-form" name="stars" id="stars">
                <option></option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
            <br>
            <label id="from">From date</label>
            <input type="date" name="fromDate"  id="fromDate" class="date" data-date-split-input="true" required>
            <br>
            <label id="to">To date</label>
            <input type="date" name="toDate" id="toDate" class="date" data-date-split-input="true" required>

            <label>Rooms and number of places</label>
            <div id="rooms">
                <input type="number" id="numOfTravelers" min="1" max="4" value="2" required>
            </div>

            <input type="button" name="addRoom" id="addRoom" value="Add room">
            <input type="button" name="search" id="search" value="Search">
        </form>
    </div>
</div>