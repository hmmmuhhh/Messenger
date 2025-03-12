function getCar() {
    fetch("http://localhost:8080/data", {
        method: "GET"
    }).then(response => response.json())
        .then(json => insertHTML("get-car-info", json))
        .catch(err => console.error(err));
}

async function createCar() {
    const car = {
        brand: "Honda",
        model: "Civic",
        year: 2020
    };
    const response = await fetch("http://localhost:8080/data", {
        method: "POST",
        body: JSON.stringify(car)
    });
    const json = await response.json();
    insertHTML("create-car-info", json);
}

function insertHTML(id, json) {
    const e = document.getElementById(id);
    e.innerHTML = `<span>${json.brand}</span><br/><span>${json.model}</span><br/><span>${json.year}</span>`;
}