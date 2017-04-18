clear all;
close all;
clc;
format compact;

coeff_range=[1 5 10 15 20 25 30 35 40 45 50];
static_coeff=1;
Ks_by_a=zeros(3, 11);
Ks_by_b=zeros(3, 11);

Q=[1 0; 0 static_coeff];
for i=1:11
    Q(1, 1)=coeff_range(i);
    Ks_by_a(:, i)=coeffs(Q);
end

Q=[static_coeff 0; 0 1];
for i=1:11
    Q(2, 2)=coeff_range(i);
    Ks_by_b(:, i)=coeffs(Q);
end

Ks_by_a

figure;
hold on;
subplot(3,2,1); plot(coeff_range, Ks_by_a(1, :))
ylabel('k0');
subplot(3,2,3); plot(coeff_range, Ks_by_a(2, :))
ylabel('k1');
subplot(3,2,5); plot(coeff_range, Ks_by_a(3, :))
ylabel('g');
xlabel('a');
subplot(3,2,2); plot(coeff_range, Ks_by_b(1, :))
axis([0 50 0.09 0.11]);
subplot(3,2,4); plot(coeff_range, Ks_by_b(2, :))
subplot(3,2,6); plot(coeff_range, Ks_by_b(3, :))
axis([0 50 1.01 1.03]);
xlabel('b');
hold off;

Q=[1 0; 0 static_coeff];
for i=1:11
    Q(1, 1)=coeff_range(i);
    Ks_by_a(:, i)=quality(Q);
end

Q=[static_coeff 0; 0 1];
for i=1:11
    Q(2, 2)=coeff_range(i);
    Ks_by_b(:, i)=quality(Q);
end

figure;
hold on;
subplot(3,2,1); plot(coeff_range, Ks_by_a(1, :))
ylabel('Max root');
subplot(3,2,3); plot(coeff_range, Ks_by_a(2, :))
ylabel('Omega');
subplot(3,2,5); plot(coeff_range, Ks_by_a(3, :))
ylabel('Min abs real');
xlabel('a');
subplot(3,2,2); plot(coeff_range, Ks_by_b(1, :))
subplot(3,2,4); plot(coeff_range, Ks_by_b(2, :))
axis([0 50 2.25 2.27]);
subplot(3,2,6); plot(coeff_range, Ks_by_b(3, :))
xlabel('b');
hold off;

syms W p t

max = 5;
min = 0;
interval = 0.005;

k0=[0.1 2.42];
k1=[4.28 5.14];
g=[1.02 1.48];

for i=1:2
W = g(i)*(5*p+5)/(p^2+(k1(i)-2)*p+k0(i)+5);
lap = vpa(ilaplace(W, p, t));
array = subs(lap, t, min:interval:max);

figure;
plot(min:interval:max, array);
end



