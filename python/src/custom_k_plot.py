import matplotlib.pyplot as plt
import pylab
import matplotlib
# matplotlib.rcParams['pdf.fonttype'] = 42
# matplotlib.rcParams['ps.fonttype'] = 42
# matplotlib.rcParams['text.usetex'] = True

overall_avg=[0.19285669450958567,0.19850718080997468,0.19842775613069535,0.17461004406213762,0.18379717494050662,0.1713785616060098,0.1837721181412538,0.17728625213106475,0.18579342663288115,0.18441052163640656,0.18301719104250272,0.1831632306178411,0.18636388331651688,0.18352907995382947,0.1825678716103236]

ks = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
fig = plt.figure()
ax = plt.subplot(111)
ax.plot(ks, overall_avg, label="overall_avg", marker='s', color='g')
ax.tick_params(axis='both', which='major')

ax.set_xlabel("k nearest neighbors")
ax.set_ylabel("Median error rate")
ax.set_title("Effect of Varying k on Error Rate")
pylab.ylim([0.04,.21])
plt.legend()
# fig.savefig('images/vary_k.pdf', format='pdf')
plt.show()

