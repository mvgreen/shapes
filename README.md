# Shapes
Just some funny things that I was not too lazy to implement.

# 'Modulo Circle'
This shape is a circle that is divided into segments by equally distant points. Each point represents a number from 0 to N. The circle itself is a kind of "bent" coordinate axis. Then we draw a line from these points to points with a coordinate equal to the result of multiplying this point's coordinate and some constant coefficient M modulo (N + 1). <br>
If we start increasing M, we will see something like this:<br><br>
![](mod_circle.gif)<br>
<i>(I'd recommend you to squint a little to see the pattern better)</i>
