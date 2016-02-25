#
zz <- file("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\ResultsRTwoAnnotators.Rout", open = "wt")
sink(zz)

source("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\english\\EnglishStats_2.r")
source("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\dutch\\DutchStats_2.r")
source("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\german\\GermanStats_2.r")
source("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\swedish\\SwedishStats_2.r")

zz <- file("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\ResultsRMoreThanTwoAnnotators.Rout", open = "wt")
sink(zz)

source("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\french\\FrenchStats.r")

sink()
