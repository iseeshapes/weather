function colourScale(percent) {
  percent *= 100;

  var red = 0, green = 0, blue = 0;
  if (percent <= 0) {
    blue = 1;
  } else if (percent > 100) {
    red = 1;
  } else if (percent > 50) {
    var temp = percent - 50
    red = (temp / 50);
    green = ((50 - temp) / 50);
  } else if (percent < 50) {
    green = (percent / 50);
    blue = ((50 - percent) / 50);
  } else {
    green = 1;
  }

  return { red, green, blue };
}

function colourScaleCSS (percent) {
  var colour = colourScale (percent);
  colour.red *= 255;
  colour.green *= 255;
  colour.blue *= 255;
  return colour;
}

function biColourScaleCSS(percent) {
  var colour = colourScale(percent);

  colour.red = colour.red / 2;
  colour.green = colour.green / 2;
  colour.blue = colour.blue / 2;

  return {
    low: {
      red: (colour.red + 0.5) * 255,
      green: (colour.green + 0.5) * 255,
      blue: (colour.blue + 0.5) * 255
    },
    high: {
      red: colour.red * 255,
      green: colour.green * 255,
      blue: colour.blue * 255
    }
  };
}
