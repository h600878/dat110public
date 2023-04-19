// Kjører en gang ved oppstart
void setup()
{
  // Setter opp input og output pins for enheten
  Serial.begin(9600);
  pinMode(12, INPUT); // knapp for å slå av og på
  pinMode(13, OUTPUT); // LED for å vise at enheten er aktiv
  pinMode(9, OUTPUT); // PWM pin for gult lys (kan dimmes)
}

// Deklarasjon av konstanter og variabler

// Enheten kan enten være aktiv eller inaktiv
const int ACTIVE = 1;
const int INACTIVE = 2;
int state = INACTIVE;

// Sikrer at knappen blir håndtert kun en gang, selv om man holder den nede
int pushhandled = 0;

int potval;
int photoval;
byte brightness = 0;

byte debug = 0;

// Kjører i en evig løkke
void loop()
{

  int btn = digitalRead(12); // Leser verdien fra knappen (LOW / HIGH)

  if (btn == LOW)
    pushhandled = 0;

  switch (state) {

    // Hvis enheten er inaktiv, sjekk om knappen blir trykket ned
    case INACTIVE:

      if ((btn == HIGH) && (!pushhandled)) {

        pushhandled = 1;
        state = ACTIVE;
        digitalWrite(13, HIGH); // Setter LED i pin 13 til HIGH, som starter lyset
      }
      break;

    // Hvis enheten er aktiv, sjekk potensionmeter, photoresistor og dim lys.
    // Deretter sjekk om knappen blir trykket ned igjen.
    case ACTIVE:

      // Leser analoge inputs
      // Vi får et tall mellom 0 og 1023. 0 er 0V og 1023 er 5V.
      potval = analogRead(A0); // Leser potensialmeteret
      photoval = analogRead(A1); // Leser photoresistoren (lyssensor)

      if (photoval < potval) {

         // Setter lysstyrken til differansen mellom potensialmeteret og photoresistoren, men maks 255
         brightness = min(potval - photoval, 255);

      } else {
        brightness = 0;
      }

      analogWrite(9, brightness); // Setter lysstyrken til det gule lyset

      if ((btn == HIGH) && (!pushhandled)) {

        pushhandled = 1;
        state = INACTIVE;
        digitalWrite(13, LOW); // Setter LED i pin 13 til LOW, som slår av lyset
        analogWrite(9, 0);
      }
    break;
  }

  // Debugging control via serial
  if (Serial.available() > 0) {
     byte input = Serial.read();
     debug = (input == 49); // ASCII 49 = 1
  }

  // Debugging output via serial
  if (debug) {
    Serial.print(photoval);
    Serial.print("<");
    Serial.print(potval);
    Serial.print(":");
    Serial.print(brightness);
    Serial.println();
  }

}
