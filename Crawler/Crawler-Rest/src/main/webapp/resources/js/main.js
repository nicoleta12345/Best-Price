// The root URL for the RESTful services
var rootURL = "http://localhost:8080/Crawler-Rest/products";

var currentProduct;

// Register listeners
$('#btnSearch').click(function() {
	search($('#searchKey').val());
	return false;
});

// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function(e) {
	if (e.which == 13) {
		search($('#searchKey').val());
		e.preventDefault();
		return false;
	}
});

function search(searchKey) {
	findByCode(searchKey);
}

function findByCode(searchKey) {
	$.ajax({
		type : 'GET',
		url : rootURL + '/search/' + searchKey,
		dataType : "json",
		success : renderList
	});
}

function renderList(data) {
	var res = [];
	for (i in data) {
		var x = data[i].x;
		var y = data[i].y;
		res[i] = [ x, y ];
	}

	var myChart = new JSChart('chartcontainer', 'line');
	myChart.setDataArray(res);
	myChart.draw();
}
