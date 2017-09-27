/**
 * Created by faiter on 6/27/17.
 */

var navContainer = new Vue({
    el: "#navContainer"
})

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
    },
    methods: {
        getResult: function () {

            $.get("/polls/"+id+"/result", function (data) {

                //console.log(data)

                seeResultDiv.result = data.title;

                //chart.update(chartData)

                $.get("/polls/"+id+"/stats", function (data) { //

                    var dist = data.optionRankVoteMap;

                    //console.log(JSON.stringify(data))

                    var options = optionsDiv.options

                    var cdata = []

                    for(var i = 0; i<options.length; i++) { // for ranks

                        var series = []

                        for (var j = 0; j < options.length; j++) { // for options

                            var title = options[j].title
                            var listOfRankVotes = dist[title]

                            var voteObj = listOfRankVotes.find(rankVote => rankVote.rank == i+1)


                            if (voteObj) {
                                console.log("Rank: " + (i + 1) + " - Option: " + title + " - Votes: " + JSON.stringify(voteObj) + " num: " + voteObj.votes)

                                series[j] = {name: "Rank "+(i+1),data:voteObj.votes}

                            }
                            else {
                                series[j] = {name:'',data:0}
                            }
                        }
                        cdata[i] = series
                    }

                    console.log("CData: "+JSON.stringify(cdata))

                    chartData.series = cdata

                    console.log("Updating chart!")
                    chart.update(chartData)

                });

            });


        },
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
        series: []
    };


    chart = new Chartist.Bar('.ct-chart', {}, {
        stackBars: true,
        axisY: {
            onlyInteger: true,
            /*labelInterpolationFnc: function(value) {
                return (value / 1000) + 'k';
            }*/
        },
        fullWidth: true,
        chartPadding: {
            right: 40
        },
        plugins: [
            Chartist.plugins.legend({

                clickable: false,

                legendNames: getLegendNames(),

                position: document.getElementById("legend")


            })
        ]
    }).on('draw', function(data) {
        if(data.type === 'bar') {
            data.element.attr({
                style: 'stroke-width: 30px'
            });
        }
    });
})


function getToHash() {
    var toHash = []
    optionsDiv.options.forEach((o)=>{
        toHash.push(o.title)
    })
    toHash.push(id)

    return JSON.stringify(toHash)
}

function getLegendNames() {

    var out = []

    for(var i=0; i< optionsDiv.numberOfOptions; i++){

        out.push("Rank "+(i+1))
    }
    console.log("Legend Names: "+optionsDiv.numberOfOptions)
    console.log(out)
    return out
}


