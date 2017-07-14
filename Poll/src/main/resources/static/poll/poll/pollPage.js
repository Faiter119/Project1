/**
 * Created by faiter on 6/27/17.
 */
var id = window.location.href.split('?')[1].split('=')[1]; // a bit ghetto

console.log("id is: "+id)

var optionsDiv = new Vue({
    el: '#optionsDiv',
    data: {
        title: "Untitled",
        votingSystem: 'Voting System',
        options: [],
        selected: "",
        numberOfOptions: 0
    },

});

var submitVoteDiv = new Vue({
    el: '#submitVoteDiv',
    data: {

        disabled: false

    },
    computed:{

        isDisabled: function () {

            return (this.disabled || hasCookieHashed(getToHash()))

        }

    },
    methods: {

        submitVote: function () {

            console.log("Submit!")

            console.log(JSON.stringify(optionsDiv.options)+" - "+optionsDiv.selected)

            var data = [] // title, rank

            optionsDiv.options.forEach(option=>{

                console.log(option)

                if(option.rank && parseInt(option.rank)){ // has rank and is valid

                    data.push({option:{title:option.title}, rank: option.rank})

                }
                else{

                    if(optionsDiv.selected == option.title){

                        console.log("Pushing: "+option)
                        data.push({option:{title: option.title}, rank: 1})

                    }
                }
            })

            console.log(data)

            if(data) {

                $.ajax({
                    url: "/polls/" + id + "/votes",
                    type: "POST",

                    headers: {
                        Accept: "Application/JSON",
                        "Content-Type": "Application/JSON"
                    },

                    data: JSON.stringify(data),

                    success: function (response) {

                        submitVoteDiv.disabled = true

                        setCookieHashed(getToHash())


                        console.log("Success");
                        console.log(response);
                    },
                    error: function (error) {

                        console.log(error)

                    }
                });
            }
        }
    },

})

var seeResultDiv = new Vue({
    el: '#seeResultDiv',
    data: {

        result: null,
        chartData: null,


    },
    methods: {
        getResult: function () {

            console.log("Get Result!")

            $.get("/polls/"+id+"/result", function (data) {

                console.log(data)

                seeResultDiv.result = data.title;

                //chart.update(chartData)

            });

            $.get("/polls/"+id+"/stats", function (data) {

                var dist = data.optionVoteDistribution;

                console.log(JSON.stringify(data))

                var options = optionsDiv.options

                var cdata = []

                for(var i = 0; i<options.length; i++){

                    var title = options[i].title

                    var numOfVotes = dist[title]

                    cdata[i] = numOfVotes

                    console.log(title+":"+numOfVotes)

                }

                chartData.series = [cdata]
                seeResultDiv.chartData = chartData // makes chart render properly on update
            });
        },
    },
    watch:{

        chartData: function (newChartData) {

            console.log("Updating chart!")

            chart.update(newChartData)

        }

    }
});


var chartData;
var chart;

$.get("/polls/"+id, function (data) {

    console.log(data)

    optionsDiv.title = data.title

    document.title = (data.title)

    optionsDiv.votingSystem = data.pollType
    optionsDiv.options = data.options

    optionsDiv.numberOfOptions = data.options.length

    var titles = []
    data.options.forEach((option)=>{
        titles.push(option.title)
    })


    chartData = {
        // A labels array that can contain any sort of values
        labels: titles,
        // Our series array that contains series objects or in this case series chartData arrays
        series: [

        ]
    };

// Create a new line chart object where as first parameter we pass in a selector
// that is resolving to our chart container element. The Second parameter
// is the actual chartData object.
    chart = new Chartist.Bar('.ct-chart', chartData);

})


function getToHash() {
    var toHash = []
    optionsDiv.options.forEach((o)=>{
        toHash.push(o.title)
    })
    toHash.push(id)

    return JSON.stringify(toHash)
}



