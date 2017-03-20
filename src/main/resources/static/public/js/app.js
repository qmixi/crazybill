(function () {
    var form = document.getElementById('new-bill');
    var links = document.getElementById('links');
    var indicator = document.getElementById('indicator');
    var addPosition = document.getElementById('add-position');
    var positions = document.getElementById('positions');

    form.onsubmit = function (ev) {
        ev.preventDefault();
        var data = new FormData(form);
        var json = {
            name: data.get('name'),
            positions: []
        };

        beginFetch();

        fetch('/bills', {
            method: 'POST',
            body: JSON.stringify(json),
            headers: {
                'Content-type': 'application/json'
            }
        })
        .then(addLinkToNewBill.bind(json))
        .catch(handleError);
    };

    function createElement(tagName, attributes) {
        var el = document.createElement(tagName);
        for (var key in attributes) {
            if (attributes.hasOwnProperty(key)) {
                el[key] = attributes[key];
            }
        }
        return el;
    }

    function beginFetch () {
        indicator.classList.add('active');
    }

    function endFetch() {
        indicator.classList.remove('active');
    }

    function addLinkToNewBill (response) {
        var link = createElement('a', {
            href: response.headers.get('location'),
            innerHTML: this.name,
            target: '_blank',
            className: 'link'
        });

        links.appendChild(link);
        endFetch();
    }

    function handleError (response) {
        console.error(response);
        endFetch()
    }

    function appendChildren(parent, children) {
        for (var i = 0; i < children.length; i++) {
            parent.appendChild(children[i]);
        }
    }

    addPosition.onclick = function () {
        var positionsLength = document.querySelectorAll('.position').length + 1;

        var wrapper = createElement('div', {
            className: 'position'
        });
        var header = createElement('h4', {
            innerHTML: 'Produkt numer ' + positionsLength
        });
        var positionName = createElement('input', {
            name: 'position-name'
        });
        var positionPrice = createElement('input', {
            name: 'position-price'
        });
        var labelForName = createElement('label', {
            htmlFor: 'position-name',
            innerHTML: 'nazwa produktu'
        });
        var labelForPrice = createElement('label', {
            htmlFor: 'position-price',
            innerHTML: 'cena produktu'
        });

        appendChildren(wrapper, [header, labelForName, positionName, labelForPrice, positionPrice]);

        positions.appendChild(wrapper);
    }
})();
