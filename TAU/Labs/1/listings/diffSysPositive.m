function df = diffSysPositive(t, y)
    a0 = 0.75;
a1 = 2;
b0 = 0;
b1 = 1;
    
    % t > 0 => x'' + a1 * x' + a0 * x = b0
    % Y(2) = x' && Y(1) = x
    % Y'(1) = Y(2) && Y'(2) = b0 - a1 * Y(2) - a0
    df = [y(2); b0 - a1 * y(2) - a0];
end