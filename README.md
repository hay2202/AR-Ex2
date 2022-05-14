# Robotics - Bersheet Landingg

## The story of the crash

As with any space accident,
the crash of Beresheet was due to a sequence of problems,
some of which were caused by poor design of the spacecraft which was very small and made of relatively inexpensive materials,
and some of which were caused by human error.
On February 22, 2019, the Beresheet spacecraft was launched into space in order to make history and land on the moon.
On the night of the launch, a malfunction was found in a pair of cameras designed to photograph the sky,
identify certain stars and thus determine the angle of the spacecraft in space.
That is, in which direction the spacecraft is moving.
A few days after launch, another malfunction was found, when the spacecraft's computer rebooted itself and postponed a planned maneuver of the spacecraft.
The boot problem continued to accompany the spacecraft on its way to the moon, which was probably caused by the effect of radiation on the function of the electronic interface box.
This is a result of using cheap parts that have never been tested in space.

On April 11, 2019, after several orbits around the moon, the spacecraft began preparations for landing on the moon.
This process is designed to slow down the spacecraft and allow it to land after about 20 minutes.
Already after about eight minutes from the start of the landing process a chain of faults began.
At an altitude of about 14 km above the moon, with the engine running all the time, the accelerometer (inertial unit of measurement (IMU)) was turned off.
The spacecraft had two such accelerometers, so the malfunction was not critical to mission success as it performed well with one.
At this point the team had to make a decision whether to continue with a single accelerometer or try to reactivate that layer.
It was decided to try to restart, which inadvertently caused the spacecraft computer to reboot and eventually as a result the engine that was supposed to always run shut down and slow down the landing.
At this point, when the landing speed was about 900 meters per second, braking was impossible, and the spacecraft crashed violently on the moon.

## About the simulation

### The structure of the simulation
the Beresheet main class contains all the parameters of the original landing course of the Beresheet spacecraft.
Inside the class there is a loop that continues until the moment the spacecraft reaches a height of 0,
inside it we update the parameters so that the spacecraft will make the landing in the best way.

Results report:
[Landing Report](https://github.com/hay2202/AR-Ex2/tree/main/src/landing_report.txt)
