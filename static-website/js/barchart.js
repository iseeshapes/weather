function BarChart (values) {
  this.values = values;
  this.min = Number.POSITIVE_INFINITY;
  this.max = Number.NEGATIVE_INFINITY;
  this.average = 0;

  var i;
  for (i=0;i<this.values.length;i++) {
    if (this.values[i] < this.min) {
      this.min = this.values[i];
    }
    if (this.values[i] > this.max) {
      this.max = this.values[i];
    }
    this.average += this.values[i];
  }
  this.average /= this.values.length;

  this.scale = function (value, height) {
    var result = (value - this.min)/(this.max - this.min);
    return (result * height * 0.8) + (height * 0.1);
  }

  this.draw = function (ctx, width, height) {
    var i, x=0, y, colour, barColour, percent;
    const barWidth = width / values.length;
    const midHeight = this.scale(this.average, height);

    for (i=0;i<values.length;i++) {
      percent = (this.values[i] - this.min) / (this.max - this.min);
      colour = colourScale(percent);

      barColour = "rgb(" + colour.red * 100 + "%," + colour.green * 100 + "%," + colour.blue * 100 + "%)";
      ctx.fillStyle = barColour;
      y = midHeight - this.scale (this.values[i], height);
      ctx.fillRect(x, midHeight, barWidth, y);
      x += barWidth;
    }
  }
}
