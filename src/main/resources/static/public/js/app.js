(function() {
    var button = document.getElementById('add');

    var HEADERS = {
        'Content-Type': 'application/json'
    };

    var bill = {
        name: 'test bill',
        positions: [{
            name: 'piwo',
            price: 20,
            persons: [{
                name: 'Jarek'
            }, {
                name: 'Michał'
            }]
        }]
    };

    button.onclick = function () {
        fetch('/bills/', {
                method: 'POST',
                body: JSON.stringify(bill),
                headers: HEADERS
            })
            .then(function (response) {
                window.resp = response;
            })
            .catch(console.error)
    };
})();
