
source("C:\\Users\\JoseAntonio\\Documents\\R\\win-library\\3.2\\agree.coeff2.r")
source("C:\\Users\\JoseAntonio\\Documents\\R\\win-library\\3.2\\weights.gen.r")

coin <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\english\\coincidences_ADP_S.txt", header=FALSE, na.string="")
weig <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\english\\weights_ADP_S.csv", header=FALSE, na.string="")
cat("\n\nADOPT SCENARIO, ENGLISH and STRICT annotation\n");
coincidences <- as.matrix(coin)
weights <- as.matrix(weig)
krippen2.table(coincidences, weights)

coin <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\english\\coincidences_ADP_L.txt", header=FALSE, na.string="")
weig <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\english\\weights_ADP_L.csv", header=FALSE, na.string="")
cat("\n\nADOPT SCENARIO, ENGLISH and LOOSE annotation\n");
coincidences <- as.matrix(coin)
weights <- as.matrix(weig)
krippen2.table(coincidences, weights)

coin <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\english\\coincidences_ALT_S.txt", header=FALSE, na.string="")
weig <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\english\\weights_ALT_S.csv", header=FALSE, na.string="")
cat("\n\nALTERNATIVE SCENARIO, ENGLISH and STRICT annotation\n");
coincidences <- as.matrix(coin)
weights <- as.matrix(weig)
krippen2.table(coincidences, weights)

coin <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\english\\coincidences_ALT_L.txt", header=FALSE, na.string="")
weig <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\english\\weights_ALT_L.csv", header=FALSE, na.string="")
cat("\n\nALTERNATIVE SCENARIO, ENGLISH and LOOSE annotation\n");
coincidences <- as.matrix(coin)
weights <- as.matrix(weig)
krippen2.table(coincidences, weights)
