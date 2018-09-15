$(document).ready(function() {
  $('#tz').text(moment.tz.guess());
  $('#tz')[0].value = moment.tz.guess();

});

function gm_authFailure() {
  console.log("Failed")
}

function initLoc() {
  $('#loc-picker').ready(function() {
    var options ={
        types:['(regions)'],
        componentRestrictions: {country: "us"}
    };
    var input = document.getElementById('loc-picker');
    var autocomplete = new google.maps.places.Autocomplete(input, options);
  });
}
