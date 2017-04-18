function res=quality(Q)
    Cs=coeffs(Q);
    root=roots([1 Cs(2)-2 Cs(1)+5]);
    res(1,1)=max(real(root));
    res(2,1)=geomean(abs(root));
    res(3,1)=min(abs(real(root)));
end