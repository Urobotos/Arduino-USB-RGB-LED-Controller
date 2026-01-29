
////////////////// Serial Protocol //////////////////
// 
// The Arduino listens on USB serial (115200 baud)
// 
// Command format:
// L<index>:<R>,<G>,<B>
//
// Example:
// L0:255,0,0   → LED 0 red
// L1:0,255,0   → LED 1 green
// L2:0,0,255   → LED 2 blue
//
////////////////////////////////////////////////////


#include <Adafruit_NeoPixel.h>

#define LED_PIN    2
#define LED_COUNT  3
#define BAUDRATE   115200

Adafruit_NeoPixel leds(LED_COUNT, LED_PIN, NEO_GRB + NEO_KHZ800);

String input = "";

void setup() {
  Serial.begin(BAUDRATE);
  leds.begin();
  leds.show(); // turn off all LEDs
  Serial.println("NeoPixel ready");
}

void loop() {
  while (Serial.available()) {
    char c = Serial.read();

    if (c == '\r') return;   // ignore CR
    if (c == '\n') {
      parseCommand(input);
      input = "";
    } else {
      input += c;
    }
  }
}

void parseCommand(String cmd) {
  // Expected format: L<index>:<r>,<g>,<b>
  if (!cmd.startsWith("L")) return;

  int colon = cmd.indexOf(':');
  int c1 = cmd.indexOf(',', colon);
  int c2 = cmd.indexOf(',', c1 + 1);

  if (colon < 0 || c1 < 0 || c2 < 0) return;

  int index = cmd.substring(1, colon).toInt();
  int r = cmd.substring(colon + 1, c1).toInt();
  int g = cmd.substring(c1 + 1, c2).toInt();
  int b = cmd.substring(c2 + 1).toInt();

  if (index < 0 || index >= LED_COUNT) return;

  r = constrain(r, 0, 255);
  g = constrain(g, 0, 255);
  b = constrain(b, 0, 255);

  leds.setPixelColor(index, leds.Color(r, g, b));
  leds.show();

  Serial.print("LED ");
  Serial.print(index);
  Serial.print(" -> ");
  Serial.print(r);
  Serial.print(",");
  Serial.print(g);
  Serial.print(",");
  Serial.println(b);
}

