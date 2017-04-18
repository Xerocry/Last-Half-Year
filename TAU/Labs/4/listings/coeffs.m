function Cs=coeffs(Q)
A=[0 1; -5 2];
B=[0; 1];
C=[5 5];
R=1;
sys=ss(A,B,C,[0]);
K=lqr(sys, Q, R);
M=5/K(1);
g=(M+1)/M;
Cs(1,1)=K(1);
Cs(2,1)=K(2);
Cs(3,1)=g;
end