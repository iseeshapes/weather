function TemperatureChart (values) {
  const _values = values;
  var minIndex = 0;
  var maxIndex = 0;
  var average = 0;

  var i;

  for (i=0;i<_values.length;i++) {
    if (_values[i].temperature < _values[minIndex].temperature) {
      minIndex = i;
    }
    if (_values[i].temperature > _values[maxIndex].temperature) {
      maxIndex = i;
    }
    average += _values[i].temperature;
  }
  average /= _values.length;

  separation = 5;
  startTemperature = 0;
  if (_values[minIndex].temperature < 0) {
    while (startTemperature > _values[minIndex].temperature - separation / 2) {
      startTemperature -= separation;
    }
  } else {
    while (startTemperature < _values[minIndex].temperature - separation / 2) {
      startTemperature += separation;
    }
    startTemperature -= separation;
  }

  endTemperature = startTemperature;
  do {
    endTemperature += separation;
  } while (endTemperature < _values[maxIndex].temperature + separation / 2);

  var startTime = new Date(_values[0].time);
  startTime.setMilliseconds(0);
  startTime.setSeconds(0);
  var startHour = 0;
  while (startHour < startTime.getHours()) {
    startHour += 3;
  }
  startHour -= 3;
  startTime.setHours(startHour);
  startMillisecs = startTime.getTime();

  var endTime = new Date(_values[_values.length - 1].time);
  endTime.setMilliseconds(0);
  endTime.setSeconds(0);
  var endHour = 24;
  while (endHour > endTime.getHours()) {
    endHour -= 3;
  }
  endHour += 3;
  endTime.setHours(endHour);
  endMillisecs = endTime.getTime();

  scaleY = (value, height) => {
    var result = (value - startTemperature);
    result /= (endTemperature - startTemperature);
    return height - (result * (height - 20)) - 10;
  }

  scaleX = (millisecs, width) => {
    var result = (millisecs - startMillisecs) / (endMillisecs - startMillisecs);
    result = (result * (width - 50)) + 25;
    return result;
  }

  drawHorizontalGridLines = (ctx, width, height) => {
    const min = _values[minIndex].temperature;

    //ctx.lineWidth = 0.5;
    ctx.strokeStyle = "#c0c0c0";
    ctx.font = "14px Arial";
    ctx.textAlign = "left";

    var x1 = scaleX(startMillisecs, width);
    var x2 = scaleX(endMillisecs, width);
    var y = scaleY(lineTemperature, height);

    var lineTemperature = startTemperature;
    for (lineTemperature=startTemperature;lineTemperature<=endTemperature;lineTemperature += separation) {
      y = scaleY(lineTemperature, height);
      ctx.beginPath();
      ctx.moveTo (x1, y);
      ctx.lineTo (x2, y);
      ctx.stroke();

      if (lineTemperature != startTemperature && lineTemperature != endTemperature) {
        ctx.fillText (lineTemperature + "C", x1, y+5);
      }
    }
  }

  drawVerticalGridLines = (ctx, width, height) => {
    ctx.strokeStyle = "#c0c0c0";
    //ctx.lineWidth = 0.5;

    ctx.font = "14px Arial";
    ctx.textAlign = "center";

    var millisecs, x;
    const y1 = scaleY(endTemperature, height);
    const y2 = scaleY(startTemperature, height);
    for (millisecs=startMillisecs;millisecs<=endMillisecs;millisecs+=hour_in_millisecs * 3) {
      x = scaleX (millisecs, width);
      ctx.beginPath();
      ctx.moveTo (x, y1);
      ctx.lineTo (x, y2);
      ctx.stroke();

      if (millisecs != startMillisecs && millisecs != endMillisecs) {
        var date = new Date (millisecs);
        ctx.fillText (print12HourClock (date), x, y2);
      }
    }
  }

  drawCurve = (ctx, width, height) => {
    var i, points = [], curve = [];
    for (i=0;i<_values.length;i++) {
      points.push(scaleX(_values[i].time.getTime(), width));
      points.push(scaleY(_values[i].temperature, height));
    }

    const numOfSegments = 3;
    const tension = 0.5;

    for (i=2; i < (points.length - 4); i+=2) {
      for (t=0; t <= numOfSegments; t++) {
        // calc tension vectors
        t1x = (points[i+2] - points[i-2]) * tension;
        t2x = (points[i+4] - points[i]) * tension;

        t1y = (points[i+3] - points[i-1]) * tension;
        t2y = (points[i+5] - points[i+1]) * tension;

        // calc step
        st = t / numOfSegments;

        // calc cardinals
        c1 =   2 * Math.pow(st, 3)  - 3 * Math.pow(st, 2) + 1;
        c2 = -(2 * Math.pow(st, 3)) + 3 * Math.pow(st, 2);
        c3 =       Math.pow(st, 3)  - 2 * Math.pow(st, 2) + st;
        c4 =       Math.pow(st, 3)  -     Math.pow(st, 2);

        // calc x and y cords with common control vectors
        x = c1 * points[i]    + c2 * points[i+2] + c3 * t1x + c4 * t2x;
        y = c1 * points[i+1]  + c2 * points[i+3] + c3 * t1y + c4 * t2y;

        //store points in array
        curve.push(x);
        curve.push(y);
      }
    }
    curve.unshift (points[1]);
    curve.unshift (points[0]);

    curve.push(points[points.length - 2]);
    curve.push(points[points.length - 1]);

    ctx.strokeStyle = "#0000ff";
    //ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.moveTo(curve[0], curve[1]);
    for(i=2;i<curve.length-1;i+=2) ctx.lineTo(curve[i], curve[i+1]);
    ctx.stroke();
  }

  this.draw = (ctx, width, height) => {
    drawHorizontalGridLines(ctx, width, height);
    drawVerticalGridLines(ctx, width, height);

    //Draw Average
    var x1 = scaleX(startMillisecs, width);
    var x2 = scaleX(endMillisecs, width);
    ctx.strokeStyle = "#60ff60";
    ctx.beginPath();
    ctx.moveTo (x1, scaleY(average, height));
    ctx.lineTo (x2, scaleY(average, height));
    ctx.stroke();

    drawCurve(ctx, width, height)

    //Draw minimum
    ctx.strokeStyle = "#6060ff";
    ctx.beginPath();
    ctx.moveTo (0, scaleY(_values[minIndex], height));
    ctx.lineTo (width, scaleY(_values[minIndex], height));
    ctx.stroke();

    //Draw maximum
    ctx.strokeStyle = "#ff6060";
    ctx.beginPath();
    ctx.moveTo (0, scaleY(_values[maxIndex], height));
    ctx.lineTo (width, scaleY(_values[maxIndex], height));
    ctx.stroke();

    var x, y;

    ctx.fillStyle = "#2020ff";
    x = scaleX (_values[minIndex].time.getTime(), width);
    y = scaleY (_values[minIndex].temperature, height);
    ctx.beginPath();
    ctx.arc (x, y, 3, 0, 2 * Math.PI);
    ctx.fill();

    ctx.fillStyle = "#ff2020";
    x = scaleX (_values[maxIndex].time.getTime(), width);
    y = scaleY (_values[maxIndex].temperature, height);
    ctx.beginPath();
    ctx.arc (x, y, 3, 0, 2 * Math.PI);
    ctx.fill();
  }
}
