(function () {
    var app = document.getElementById('app');
    var form;
    var HEADERS = {
        'Content-Type': 'application/json'
    };

    var createElement = function (tag, attributes) {
        var element = document.createElement(tag);

        for (var key in attributes) {
            if (attributes.hasOwnProperty(key)) {
                element[key] = attributes[key];
            }
        }

        return element;
    };

    var appendChildren = function (parent, children) {
        children.forEach(function (child) {
            parent.appendChild(child);
        });
    };

    var submitForm = function (ev) {
        ev.preventDefault();
        var data = new FormData(form);
        var json = {
            name: data.get('name'),
            positions: []
        };

        fetch('/bills/', {
                method: 'POST',
                body: JSON.stringify(json),
                headers: HEADERS
            })
            .then(appendLink.bind(json))
            .catch(console.error);
    };

    var generateForm = function () {
        form = createElement('form');
        var input = createElement('input', {
            type: 'text',
            name: 'name',
            id: 'name'
        });
        var label = createElement('label', {
            htmlFor: 'name',
            innerHTML: 'Nazwa rachunku'
        });
        var button = createElement('button', {
            type: 'submit',
            innerHTML: 'Zapisz'
        });

        button.onclick = submitForm;

        appendChildren(form, [input, label, button]);
        app.appendChild(form);
    };

    var appendLink = function (response) {
        var link = response.headers.get('location');
        var a = createElement('a', {
            href: link,
            target: '_blank',
            innerHTML: this.name
        });
        app.appendChild(a);
    };

    generateForm();
})();
