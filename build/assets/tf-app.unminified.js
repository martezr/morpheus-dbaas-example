var process = process || {env: {NODE_ENV: "development"}};
console.log("Javascript Test")
document.querySelector("#instance-header-info-details").remove()
data = document.getElementById("summary").children[0].children[2]
//console.log(data)
data.remove()

tee = document.querySelector("#app-header-info > div.page-resource-header > div.header-meta-info")
console.log(tee)
// Remove costing summary section
costing = document.getElementById("summary").children[1]
costing.remove()
document.querySelector("#summary > div.app-data-summary > div.row.chart-types").remove()
appTabs = document.querySelector("#nav-tabs-wrapper").children
console.log(appTabs)
for (const item of appTabs) {
    console.log(item.innerText);
    if (item.innerText == "Monitoring") {
        item.remove()
    }
    if (item.innerText == "Logs") {
        console.log("Found logs")
        item.remove()
    }
    if (item.innerText == "Environment") {
        item.remove()
    }
    if (item.innerText == "Security Groups") {
        console.log("Found security groups")
        item.remove()
    }
    if (item.innerText == "Resources") {
        item.remove()
    }
}

//monitoring = document.querySelector("#nav-tabs-wrapper > li:nth-child(4)")

// Update the order form submit action
/*
document.addEventListener('DOMContentLoaded', function() {
    catalogId = document.body.attributes.getNamedItem("data-id").value
    url = `/service-catalog/catalog/${catalogId}/add`
    orderForm = document.getElementsByClassName("order-form")[0]
    orderForm.action = url;
  });
  */
