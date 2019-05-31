%  
% NubilaPardus
%
thread = [2 
3 
5 
8 
10
12
16];

avg_5e5=[465.48873899999995
555.8302853333333
1157.5482784
2090.7526955
2617.5594512
2973.4495578333335
3537.6751921249997];

avg_1e5=[1088.23762
859.28892
1517.7471799999998
2273.65702125
2432.381974
2715.2745508333333
3578.4488137499998];

avg_5e4=[1108.7777999999998
1215.12432
1420.9886040000001
2608.2171424999997
2589.910548
2564.0004050000002
3115.96922375];

avg_1e4=[3492.24965
3142.8524666666667
2451.40798
2693.43685
2917.1454299999996
3831.436008333334
2517.739875];

avg_5e3=[3474.5615
2839.5848666666666
3086.0605600000004
4216.257825
3634.42036
3567.86055
5123.765524999999];


figure(3)
grid on
hold on
xlim([0,17])
plot(thread, avg_5e5, 'c-o')
plot(thread, avg_1e5, 'b-*')
plot(thread, avg_5e4, 'm-p')
plot(thread, avg_1e4, 'r-+')
plot(thread, avg_5e3, 'k-+')
legend({'# of iteration : 5*10^5', '# of iteration : 10^5', '# of iteration : 5*10^4', '# of iteration : 10^4', '# of iteration : 5*10^3'},'Location','northwest')
title('Average time for Pop and Push operation for each iteration')
xlabel('Number of thread')
ylabel('Average Time (nanosec) for each thread iteration')



