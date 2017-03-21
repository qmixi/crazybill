(function () {
    var form = document.getElementById('new-bill');
    var links = document.getElementById('links');
    var indicator = document.getElementById('indicator');
    var error = document.getElementById('error');
    var submitButton = document.getElementById('submit-button');

    form.onsubmit = function (ev) {
        onSubmitStart();

        ev.preventDefault();
        var data = new FormData(form);
        var json = {
            name: data.get('name'),
            positions: [{
                name: data.get('product-name'),
                price: data.get('product-price'),
                persons: getPersons(data.get('product-persons'))
            }]
        };

        fetch('/bills', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(json)
        })
            .then(addLinkToNewBill.bind(json))
    };

    function addLinkToNewBill (response) {
        var location = response.headers.get('location');
        if (!location) {
            handleError(response);
            return;
        }

        var link = document.createElement('a');
        link.href = response.headers.get('location');
        link.innerHTML = this.name;
        link.target = '_blank';
        link.className = 'link';

        links.appendChild(link);
        onSubmitEnd();
    }

    function displayError(response) {
        error.classList.add('active');
        error.innerHTML = response.error;
    }

    function hideError() {
        error.classList.remove('active');
    }
    function handleError (response) {
        response.json()
            .then(displayError);
        onSubmitEnd();
    }

    function onSubmitStart() {
        hideError();
        indicator.classList.add('active');
        submitButton.disabled = true;
    }

    function onSubmitEnd() {
        indicator.classList.remove('active');
        submitButton.disabled = false;
    }

    function getPersons (persons) {
        var personArray = persons.split(';');
        var result = [];

        personArray.forEach(function (person) {
            result.push({
                name: person
            })
        });
        return result;
    }
})();
