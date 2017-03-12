a0 = 0.75;
a1 = 2;
b0 = 0;
b1 = 1;

numerator = [b1 b0];
denominator = [1 a1 a0];
Wp = tf(numerator, denominator, 'OutputName', 'torque', 'Variable','p')

syms x1;
syms p;
syms y;
syms u;
r=solve('u=x1(p^2)+2*p+0.75', 'y=x1*p');

% A=[0 1; -0.75 -2];
% 
U1 = [0 1; 1 -2];
U2 = [0 -0.75; 1 -2];

U3 = [1.5 -2.25; -0.5 0.25];

invU1 = inv(U1);

invU2 = inv(U2);

invU3 = inv(U3);

P1 =U2*inv(U1)

P2 = U3*inv(U1)

P3 = U1*inv(U2)

P4 = U3*inv(U2)

P5 = U1*inv(U3)

P6 = U2*inv(U3)

C = [0 1];

A = [0 0; -0.75 -2];

pole(Wp)

% A1=[0 1; -0.75 -2];
% B1=[0; 1];
% C1=[0 1];
% 
% A2=[0 -0.75; 1 -2];
% B2=[0; 1];
% C2=[0 1];
% 
% A3=[-3/2 0; 0 -1/2];
% B3=[1.5; -0.5];
% C3=[1 1];
% 
% E = [1 0; 0 1];
% 
% Invvv = C3 * inv(p*E-A3) * B3
% 



