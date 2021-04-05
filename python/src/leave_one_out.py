#
# AUTO-GENERATED FILE. DO NOT EDIT
# CodeBuff 1.5.1 'Tue Mar 30 17:21:23 CDT 2021'
#
import numpy as np
import pylab
import matplotlib.pyplot as plt

java_st_err = [0.028037382, 0.0, 0.062893085, 0.024449877, 0.12195122, 0.013661202, 0.11627907, 0.04506699, 0.01898734, 0.06347439, 0.183, 0.05207329, 0.028478438, 0.059854016, 0.07968128, 0.026634382, 0.026402641, 0.05756014, 0.034246575, 0.027777778, 0.0091743115, 0.05263158, 0.054545455, 0.10998878, 0.061946902, 0.052200615, 0.0453125, 0.032, 0.029411765, 0.023041476, 0.009433962, 0.09023179, 0.13064133, 0.07917059, 0.024096385, 0.05376344, 0.1641791, 0.040625, 0.1641791, 0.046296295, 0.05871212, 0.05352798, 0.2054054, 0.07159353, 0.028037382, 0.036842104, 0.0, 0.0, 0.018867925, 0.015873017, 0.053982303, 0.23655914, 0.09917355, 0.053435113, 0.15625, 0.0914787, 0.05780822, 0.04668838, 0.05963397]
java_custom_err = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]

language_data = [java_st_err, java_custom_err]
labels = ["java_st\nn=59", "java_custom\nn=8"]
fig = plt.figure()
ax = plt.subplot(111)
ax.boxplot(language_data,
           whis=[10, 90], # 10 and 90 % whiskers
           widths=.35,
           labels=labels,
           showfliers=False)
ax.set_xticklabels(labels, rotation=60, fontsize=18)
ax.tick_params(axis='both', which='major', labelsize=18)
plt.xticks(range(1,len(labels)+1), labels, rotation=60, fontsize=18)
pylab.ylim([0,.28])
ax.yaxis.grid(True, linestyle='-', which='major', color='lightgrey', alpha=0.5)
ax.set_xlabel("Grammar and corpus size", fontsize=20)
ax.set_ylabel("Misclassification Error Rate", fontsize=20)
# ax.set_title("Leave-one-out Validation Using Error Rate\nBetween Formatted and Original File")
plt.tight_layout()
fig.savefig('images/leave_one_out.pdf', format='pdf')
plt.show()
