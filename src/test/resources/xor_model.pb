
D

my_input/XPlaceholder*
shape:���������*
dtype0
a
FullyConnected/WConst*9
value0B." �J�U���ȽR�;�c���K�qZ��<տ.]H?*
dtype0
a
FullyConnected/W/readIdentityFullyConnected/W*
T0*#
_class
loc:@FullyConnected/W
M
FullyConnected/bConst*%
valueB"}L@^^�?=\�?R�>*
dtype0
a
FullyConnected/b/readIdentityFullyConnected/b*
T0*#
_class
loc:@FullyConnected/b
q
FullyConnected/MatMulMatMul
my_input/XFullyConnected/W/read*
transpose_b( *
T0*
transpose_a( 
o
FullyConnected/BiasAddBiasAddFullyConnected/MatMulFullyConnected/b/read*
T0*
data_formatNHWC
B
FullyConnected/SigmoidSigmoidFullyConnected/BiasAdd*
T0
s
FullyConnected_1/WConst*I
value@B>"0iУ?��@*�z�H�(�'si����@���>^?�Q��}��?��?�+�*
dtype0
g
FullyConnected_1/W/readIdentityFullyConnected_1/W*
T0*%
_class
loc:@FullyConnected_1/W
K
FullyConnected_1/bConst*!
valueB"��۾P���0.%@*
dtype0
g
FullyConnected_1/b/readIdentityFullyConnected_1/b*
T0*%
_class
loc:@FullyConnected_1/b
�
FullyConnected_1/MatMulMatMulFullyConnected/SigmoidFullyConnected_1/W/read*
transpose_b( *
T0*
transpose_a( 
u
FullyConnected_1/BiasAddBiasAddFullyConnected_1/MatMulFullyConnected_1/b/read*
T0*
data_formatNHWC
F
FullyConnected_1/SigmoidSigmoidFullyConnected_1/BiasAdd*
T0
H
my_output/WConst*%
valueB"f�K@�h�@	�*
dtype0
R
my_output/W/readIdentitymy_output/W*
T0*
_class
loc:@my_output/W
<
my_output/bConst*
valueB*���*
dtype0
R
my_output/b/readIdentitymy_output/b*
T0*
_class
loc:@my_output/b
u
my_output/MatMulMatMulFullyConnected_1/Sigmoidmy_output/W/read*
transpose_b( *
T0*
transpose_a( 
`
my_output/BiasAddBiasAddmy_output/MatMulmy_output/b/read*
T0*
data_formatNHWC
8
my_output/SigmoidSigmoidmy_output/BiasAdd*
T0 