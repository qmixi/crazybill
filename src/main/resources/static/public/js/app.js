(function () {
    var form = document.getElementById('new-bill');
    var links = document.getElementById('links');
    var indicator = document.getElementById('indicator');
    var newPosition = document.getElementById('new-position');

    form.onsubmit = function (ev) {
        onSubmitBegin();
        ev.preventDefault();
        var data = new FormData(form);
        var errorMessage = validateData(data);
        if (errorMessage) {
            alert(errorMessage);
            onSubmitEnd();
            return;
        }
        var json = {
            name: data.get('name'),
            positions: getPosition(data)
        };

        fetch('/bills', {
            method: 'POST',
            body: JSON.stringify(json),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(addLinkToNewBill.bind(json))
            .catch(console.error)
    };

    newPosition.onclick = function (ev) {
        ev.preventDefault();

        var cloned = document.getElementById('position-0').cloneNode(true);
        var positionsLength = document.querySelectorAll('#positions li').length;

        cloned.id = cloned.id.replace('0', positionsLength);

        cloned.querySelectorAll('label').forEach(function (label) {
            label.htmlFor = label.htmlFor.replace('0', positionsLength);
        });

        cloned.querySelectorAll('input').forEach(function (input) {
            input.id = input.id.replace('0', positionsLength);
            input.name = input.name.replace('0', positionsLength);
            input.value = '';
        });

        document.getElementById('positions').appendChild(cloned);
    };

    function toJson (response) {
        return response.json();
    }

    function checkStatus (json) {
        console.log(json);
        if (json.status > 400) {
            alert(json.error);
        } else {

        }
    }

    function addLinkToNewBill (response) {
        var link = document.createElement('a');
        link.href = response.headers.get('location');
        link.innerHTML = this.name;
        link.target = '_blank';
        link.className = 'link';

        links.appendChild(link);
        onSubmitEnd();
    }

    function onSubmitBegin() {
        indicator.classList.remove('hide');
    }

    function onSubmitEnd() {
        indicator.classList.add('hide');
    }

    function validateData(data) {
        var name = data.get('name');
        var message;
        if (name.length === 0) {
            message = 'Nazwa nie może być pusta';
        }
        return message;
    }

    function getPosition(data) {
        var positions = [];
        var positionsLength = document.querySelectorAll('#positions li').length;
        var i, position;

        for (i = 0; i < positionsLength; i++) {
            position = {
                name: data.get('position-name-' + i),
                price: data.get('position-price-' + i),
                persons: data.get('persons-' + i).split(',').map(function (person) {
                    return {
                        name: person
                    }
                })
            };

            positions.push(position);
        }

        return positions;
    }
})();
