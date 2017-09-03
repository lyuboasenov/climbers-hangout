#include <Bounce2.h>
#include <HX711.h>

/* sample for digital weight scale of hx711
   library design: Weihong Guan (@aguegu)
   library host on
  https://github.com/aguegu/ardulibs/tree/3cdb78f3727d9682f7fd22156604fc1e4edd75d1/hx711
*/

// Hx711.DOUT - pin #A2
// Hx711.SCK - pin #A3

HX711 scale(A2, A3);

void setup() {
  Serial.begin(38400);
  /*
    Serial.println("HX711 Demo");

    Serial.println("Before setting up the scale:");
    Serial.print("read: \t\t");
    Serial.println(scale.read());      // print a raw reading from the ADC

    Serial.print("read average: \t\t");
    Serial.println(scale.read_average(20));   // print the average of 20 readings from the ADC

    Serial.print("get value: \t\t");
    Serial.println(scale.get_value(5));   // print the average of 5 readings from the ADC minus the tare weight (not set yet)

    Serial.print("get units: \t\t");
    Serial.println(scale.get_units(5), 1);  // print the average of 5 readings from the ADC minus tare weight (not set) divided
            // by the SCALE parameter (not set yet)
  */
  scale.set_scale(15.f);    // this value is obtained by calibrating the scale with known weights; see the README for details
  scale.tare();               // reset the scale to 0

  /*
    Serial.println("After setting up the scale:");

    Serial.print("read: \t\t");
    Serial.println(scale.read());                 // print a raw reading from the ADC

    Serial.print("read average: \t\t");
    Serial.println(scale.read_average(20));       // print the average of 20 readings from the ADC

    Serial.print("get value: \t\t");
    Serial.println(scale.get_value(5));   // print the average of 5 readings from the ADC minus the tare weight, set with tare()

    Serial.print("get units: \t\t");
    Serial.println(scale.get_units(5), 1);        // print the average of 5 readings from the ADC minus tare weight, divided
            // by the SCALE parameter set with set_scale

    Serial.println("Readings:");
  */
}

float m_previousWeightValue;

void loop() {
  float currentWeightValue = scale.get_units(5);
  if (abs(m_previousWeightValue - currentWeightValue) > 10) {
    float oldWeightValue = m_previousWeightValue;
    m_previousWeightValue = currentWeightValue;
    //Serial.print("new weight:\t");
    Serial.println(currentWeightValue);
    //Serial.print("\t| old weight:\t");
    //Serial.println(oldWeightValue, 1);
  }
}
