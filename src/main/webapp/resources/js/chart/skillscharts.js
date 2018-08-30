var createPie = function( label, labels, data, colors) {
    return {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                data: data,
                backgroundColor: colors
            }]
        },
        options: {
            legend: {
              position: 'left'
            },

            animation: {
                easing: "easeOutQuart",
                onComplete: function () {
                    var ctx = this.chart.ctx;
                    ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontFamily, 'normal', Chart.defaults.global.defaultFontFamily);
                    ctx.textAlign = 'center';
                    ctx.textBaseline = 'bottom';

                    this.data.datasets.forEach(function (dataset) {

                        for (var i = 0; i < dataset.data.length; i++) {
                            var model = dataset._meta[Object.keys(dataset._meta)[0]].data[i]._model,
                                total = dataset._meta[Object.keys(dataset._meta)[0]].total,
                                mid_radius = model.innerRadius + (model.outerRadius - model.innerRadius)/1.85,
                                start_angle = model.startAngle,
                                end_angle = model.endAngle,
                                mid_angle = start_angle + (end_angle - start_angle)/2;

                            var x = mid_radius * Math.cos(mid_angle);
                            var y = mid_radius * Math.sin(mid_angle);

                            ctx.fillStyle = '#000';

                            if (i == 3){ // Darker text color for lighter background
                                ctx.fillStyle = '#444';
                            }
                            ctx.fillText(dataset.data[i], model.x + x , model.y + y);

                        }
                    });
                }
            }
        }
    };
};
var createPolar = function( label, labels, data) {

    return {
        type: 'polarArea',
        data: {
            labels: labels,
            datasets: [{
                label: label,
                backgroundColor: [

                    "rgba(255, 0, 0, 0.5)",
                    "rgba(0, 255, 0, 0.5)",
                    "rgba(0, 0, 255, 0.5)"
                ],
                pointBackgroundColor: "red",
                borderWidth: 1,
                data: data
            }]
        },
        options: {
            legend: {
                position: 'left'
            },
            scale: {
                reverse: false,
                ticks: {
                    max: 5,
                    stepSize: 0.5,
                    beginAtZero: true
                }
            }
        }
    };
};
var createRadar = function(label, labels, data) {

    return {
        type: 'radar',
        data: {
            labels: labels,
            datasets: [{
                label: label,
                backgroundColor: "rgba(0,0,255,0.5)",
                pointBackgroundColor: "red",
                borderWidth: 1,
                data: data
            }]
        },
        options: {
            responsive: true,
            legend: {
                position: 'bottom',
            },
            scale: {

                gridLines: {
                    color: ['red', 'red', 'red', 'green', 'green', 'green', 'blue', 'blue', 'blue', "black"]
                },
                ticks: {
                    max: 5,
                    beginAtZero: true
                }
            }
        }
    };

};

$(function(){
    new Chart($('#projectManagement'), createPie('Project management tools skills',   ["Git", "Maven", "Jira"], [4.0, 4.0, 3.5], ["red", "violet", "blue"]));
    new Chart($('#languages'), createRadar('Languages skills',   ["Java", "C#", "JavaScript", "HTML5", "CSS3", ],  [4.5, 4.0, 4, 4, 4]));
    new Chart($('#dbLanguages'), createPie('Database language skills',   ["MSSQL", "Oracle", "PostgreSQL", "MongoDB"], [4.5, 4, 3.5, 3.5], ["darkred", "red", "SteelBlue", "ForestGreen" ]));
    new Chart($('#javaFrameworks'), createRadar('Java frameworks knowledge',   ["JEE", "Hibernate", "Spring boot", "Spring", "RESTEasy"],  [4.5, 4.5, 3.5, 3.5, 4]));
    new Chart($('#javaTestLibraries'), createPie('Java test libraries',   ["JUnit", "Mockito", "Arquillian"],  [4.5, 4.5, 4.0], ["Brown", "LimeGreen", "DimGray"]));
});
