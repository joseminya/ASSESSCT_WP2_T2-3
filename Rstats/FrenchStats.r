
source("C:\\Users\\JoseAntonio\\Documents\\R\\win-library\\3.2\\agree.coeff3.raw.r")
source("C:\\Users\\JoseAntonio\\Documents\\R\\win-library\\3.2\\weights.gen.r")

rat <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\french\\ratings_ADP_S.txt", header=FALSE, na.string="")
wei <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\french\\weights_ADP_S.csv", header=FALSE, na.string="")
cat("\n\nADOPT SCENARIO, FRENCH and STRICT annotation\n");
ratings <- as.matrix(rat)
weights <- as.matrix(wei)
krippen.alpha.raw(ratings, weights)

rat <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\french\\ratings_ADP_L.txt", header=FALSE, na.string="")
wei <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\french\\weights_ADP_L.csv", header=FALSE, na.string="")
cat("\n\nADOPT SCENARIO, FRENCH and LOOSE annotation\n");
ratings <- as.matrix(rat)
weights <- as.matrix(wei)
krippen.alpha.raw(ratings, weights)

rat <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\french\\ratings_ALT_S.txt", header=FALSE, na.string="")
wei <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\french\\weights_ALT_S.csv", header=FALSE, na.string="")
cat("\n\nALTERNATIVE SCENARIO, FRENCH and STRICT annotation\n");
ratings <- as.matrix(rat)
weights <- as.matrix(wei)
krippen.alpha.raw(ratings, weights)

rat <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\french\\ratings_ALT_L.txt", header=FALSE, na.string="")
wei <- read.csv("F:\\projects\\assess ct\\Annotations berlin\\iaa\\Rstats\\french\\weights_ALT_L.csv", header=FALSE, na.string="")
cat("\n\nALTERNATIVE SCENARIO, FRENCH and LOOSE annotation\n");
ratings <- as.matrix(rat)
weights <- as.matrix(wei)
krippen.alpha.raw(ratings, weights)
