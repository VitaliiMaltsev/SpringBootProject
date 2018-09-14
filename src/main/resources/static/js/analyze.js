/*
This Codepen is in the Public Domain.
You can use it for whatever purpose you like, except evil.
*/

TweenLite.defaultEase = Expo.easeInOut;

var morphTimeline = new TimelineMax({repeat:-1, paused: true}),
    duration = 2;

morphTimeline
    .to('#step-one',duration,{morphSVG:{shape:"#step-two"}})
    .addPause()
    .to('#step-one',duration,{morphSVG:{shape:"#step-three"}})
    .addPause()
    .to('#step-one',duration,{morphSVG:{shape:"#step-four"}})
    .addPause()
    .to('#step-one',duration,{morphSVG:{shape:"#step-one"}})
    .addPause()
;

var activeStep = 1;
var direction = 'forward';

$('.next').on('click', function(){
    direction = 'forward';
    if(activeStep < 4){
        activeStep++;
    }else{
        activeStep = 1;
    }
    updateView();
    morphTimeline.play();
});

$('.previous').on('click', function(){
    direction = 'backward';
    if(activeStep > 1){
        activeStep--;
    }else{
        activeStep = 4;
    }
    updateView();
    morphTimeline.reverse();
});

function updateView(){
    $('.previous,.next').show();
    $('.previous,.next').css({'pointer-events': 'none', cursor: 'wait'});
    if(activeStep === 1){
        $('.previous').hide();
    }
    if(activeStep === 4){
        $('.next').hide();
    }
    $('.title').removeClass('active');
    $('.title' + activeStep).addClass('active');
    setTimeout(function(){
        $('.previous,.next').css({'pointer-events': 'all', cursor: 'pointer'});
    },(duration*1000));
}

updateView();