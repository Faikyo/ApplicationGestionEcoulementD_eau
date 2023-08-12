#!/bin/bash

########################## UpdateDateVersion #########################################
#
# author Frédéric Darboux <Frederic.Darboux@orleans.inra.fr> (2012-2015)
# version 1.01.01
# date 2015-07-15
#
# copyright License Cecill-V2 <http://www.cecill.info/licences/Licence_CeCILL_V2-en.html>
#
# (c) CNRS - Universite d'Orleans - INRA (France)
#
# This file is part of FullSWOF_UI software. 
# <https://sourcesup.renater.fr/projects/fullswof-ui/> 
#
# This file is part of FullSWOF_UI software.
# FullSWOF_UI stands for ``Full Shallow Water equations for Overland Flow. User Interface''.
# It is the graphical user interface for both FullSWOF_1D and FullSWOF_2D
# (see <https://sourcesup.renater.fr/projects/fullswof-1d/> and
# <https://sourcesup.renater.fr/projects/fullswof-2d/>).
# It helps in creating the input files, running the software and vizualizing the outputs.
#
# LICENSE
#
# This software is governed by the CeCILL license under French law and
# abiding by the rules of distribution of free software.  You can  use, 
# modify and/ or redistribute the software under the terms of the CeCILL
# license as circulated by CEA, CNRS and INRIA at the following URL
# <http://www.cecill.info>. 
#
# As a counterpart to the access to the source code and  rights to copy,
# modify and redistribute granted by the license, users are provided only
# with a limited warranty  and the software's author,  the holder of the
# economic rights,  and the successive licensors  have only  limited
# liability. 
#
# In this respect, the user's attention is drawn to the risks associated
# with loading,  using,  modifying and/or developing or reproducing the
# software by the user in light of its specific status of free software,
# that may mean  that it is complicated to manipulate,  and  that  also
# therefore means  that it is reserved for developers  and  experienced
# professionals having in-depth computer knowledge. Users are therefore
# encouraged to load and test the software's suitability as regards their
# requirements in conditions enabling the security of their systems and/or 
# data to be ensured and,  more generally, to use and operate it in the 
# same conditions as regards security. 
#
# The fact that you are presently reading this means that you have had
# knowledge of the CeCILL license and that you accept its terms.
#
##################################################################################

##################################################################################
# The present script "UpdateDateVersion" helps in maintaining FullSWOF_UI
# by updating consistently the version number and date of FullSWOF_UI
# in all relevant files, and compiling the documentation files
##################################################################################

SCRIPTVERSION="1.06.01, 2015-07-17."

# Define which command for awk
STDERR=2

#to have . as decimal separator
LANG=C

if [ $# -ne 4 ]
then
  echo "Usage: UpdateDateVersion YYYY-MM-DD X.YY.ZZ Compat_FS1d_version Compat_FS2d_version" >&$STDERR
  echo "Version" $SCRIPTVERSION >&$STDERR
  exit 1;
fi ;

# Define which command for sed
SED_CMD=sed
# Define which command for mktemp
MKTEMP_CMD=mktemp
# Define which command for cp
CP_CMD=cp
# Define which command for html2pdf
HTML2PDF_CMD=wkhtmltopdf
# Define which command for doxygen
DOXYGEN_CMD=doxygen

#Read input values
DATE=$1
VERSION=$2
COMPAT1D=$3
COMPAT2D=$4

### Check for programs
ALLHERE=0
command -v $SED_CMD >/dev/null 2>&1 || { echo >&2 "Error: program" $SED_CMD "required but not installed"; ALLHERE=1; }
command -v $MKTEMP_CMD >/dev/null 2>&1 || { echo >&2 "Error: program" $MKTEMP_CMD "required but not installed"; ALLHERE=1; }
command -v $HTML2PDF_CMD >/dev/null 2>&1 || { echo >&2 "Error: program" $HTML2PDF_CMD "required but not installed"; ALLHERE=1; }
command -v $DOXYGEN_CMD >/dev/null 2>&1 || { echo >&2 "Error: program" $DOXYGEN_CMD "required but not installed"; ALLHERE=1; }

if [ $ALLHERE -eq 1 ] #at least one program missing...
then
 exit 1 ;
fi

###Update README.txt
if [ -f README.txt ] #does the file exists?
then
  #it exists ==> update
  STRING='#### version '$VERSION', '$DATE'' ;
  Tempfile=`$MKTEMP_CMD Tempfile_XXXXXXXX` ;
  $SED_CMD "2s/.*/$STRING/" README.txt > $Tempfile && mv -f $Tempfile README.txt;

  STRING='#### Compatible with FullSWOF_1D Version '$COMPAT1D' and FullSWOF_2D Version '$COMPAT2D'.' ;
  Tempfile=`$MKTEMP_CMD Tempfile_XXXXXXXX` ;
  $SED_CMD "3s/.*/$STRING/" README.txt > $Tempfile && mv -f $Tempfile README.txt;
else
  #it does not exist ==> error
  echo "Error: file README.txt is not in the current directory" >&$STDERR ;
  exit 1;
fi

###Update UIMessages.properties
if [ -f ./src/l10n/ui/UIMessages.properties ] #does the file exists?
then
  #it exists ==> update
  STRING="aboutText = FullSWOF_UI<br\/>Version $VERSION ($DATE)<br\/><br\/>Compatible with FullSWOF_1D Version $COMPAT1D and FullSWOF_2D Version $COMPAT2D.<br\/><br\/>Copyright Universit\&eacute; d'Orl\&eacute;ans 2012-2015<br\/><br\/>This software is distributed under CeCILL-V2 (GPL compatible) free  software license (http:\/\/www.cecill.info\/licences\/Licence_CeCILL_V2-en.html)";
  Tempfile=`$MKTEMP_CMD Tempfile_XXXXXXXX` ;
  $SED_CMD "1,/^aboutText =.*/s/^aboutText =.*/$STRING/" ./src/l10n/ui/UIMessages.properties > $Tempfile && mv -f $Tempfile ./src/l10n/ui/UIMessages.properties;
else
  #it does not exist ==> error
  echo "Error: file UIMessages.properties is not in the ./src/l10n/ui/ directory" >&$STDERR ;
  exit 1;
fi

###Update UIMessages_fr.properties
if [ -f ./src/l10n/ui/UIMessages_fr.properties ] #does the file exists?
then
  #it exists ==> update
  STRING="configureFullswofPaths = Pour chaque configuration, veuillez indiquer la commande \&agrave utiliser pour lancer la simulation.<br\/>Note : cette version de FullSWOF_UI est compatible avec :<br\/>- FullSWOF_1D Version $COMPAT1D<br\/>- FullSWOF_2D Version $COMPAT2D";
  Tempfile=`$MKTEMP_CMD Tempfile_XXXXXXXX` ;
  $SED_CMD "1,/^configureFullswofPaths =.*/s/^configureFullswofPaths =.*/$STRING/" ./src/l10n/ui/UIMessages_fr.properties > $Tempfile && mv -f $Tempfile ./src/l10n/ui/UIMessages_fr.properties;
else
  #it does not exist ==> error
  echo "Error: file UIMessages_fr.properties is not in the ./src/l10n/ui/ directory" >&$STDERR ;
  exit 1;
fi

###Update UIMessages_en.properties
if [ -f ./src/l10n/ui/UIMessages_en.properties ] #does the file exists?
then
  #it exists ==> update
  STRING="configureFullswofPaths = For each available configuration, indicate the path to the FullSWOF version to be used.<br\/>Note: this version of FullSWOF_UI is compatible with:<br\/>- FullSWOF_1D Version $COMPAT1D<br\/>- FullSWOF_2D Version $COMPAT2D";
  Tempfile=`$MKTEMP_CMD Tempfile_XXXXXXXX` ;
  $SED_CMD "1,/^configureFullswofPaths =.*/s/^configureFullswofPaths =.*/$STRING/" ./src/l10n/ui/UIMessages_en.properties > $Tempfile && mv -f $Tempfile ./src/l10n/ui/UIMessages_en.properties;
else
  #it does not exist ==> error
  echo "Error: file UIMessages_en.properties is not in the ./src/l10n/ui/ directory" >&$STDERR ;
  exit 1;
fi

###Update Manuals
if [ -f ./src/l10n/manual/help_en.html ] #does the file exists?
then
  #it exists ==> update
  STRING="    Version $VERSION ($DATE)";
  Tempfile=`$MKTEMP_CMD Tempfile_XXXXXXXX` ;
  $SED_CMD "1,/^    Version.*/s/^    Version.*/$STRING/" ./src/l10n/manual/help_en.html > $Tempfile && mv -f $Tempfile ./src/l10n/manual/help_en.html;

  STRING="  FullSWOF_1D Version $COMPAT1D and FullSWOF_2D Version $COMPAT2D.<\/p>";
  Tempfile=`$MKTEMP_CMD Tempfile_XXXXXXXX` ;
  $SED_CMD "1,/^  FullSWOF_1D.*/s/^  FullSWOF_1D.*/$STRING/" ./src/l10n/manual/help_en.html > $Tempfile && mv -f $Tempfile ./src/l10n/manual/help_en.html;
else
  #it does not exist ==> error
  echo "Error: file help_en.html is not in the ./src/l10n/manual/ directory" >&$STDERR ;
  exit 1;
fi

if [ -f ./src/l10n/manual/help_fr.html ] #does the file exists?
then
  #it exists ==> update
  STRING="    Version $VERSION ($DATE)";
  Tempfile=`$MKTEMP_CMD Tempfile_XXXXXXXX` ;
  $SED_CMD "1,/^    Version.*/s/^    Version.*/$STRING/" ./src/l10n/manual/help_fr.html > $Tempfile && mv -f $Tempfile ./src/l10n/manual/help_fr.html;

  STRING="  FullSWOF_1D Version $COMPAT1D et FullSWOF_2D Version $COMPAT2D.<\/p>";
  Tempfile=`$MKTEMP_CMD Tempfile_XXXXXXXX` ;
  $SED_CMD "1,/^  FullSWOF_1D.*/s/^  FullSWOF_1D.*/$STRING/" ./src/l10n/manual/help_fr.html > $Tempfile && mv -f $Tempfile ./src/l10n/manual/help_fr.html;
else
  #it does not exist ==> error
  echo "Error: file help_en.html is not in the ./src/l10n/manual/ directory" >&$STDERR ;
  exit 1;
fi

#copy updated manuals
$CP_CMD ./src/l10n/manual/help_en.html ./src/l10n/manual/help.html
$CP_CMD ./src/l10n/manual/help_en.html ./doc/User_Manual_en.html
$CP_CMD ./src/l10n/manual/help_fr.html ./doc/User_Manual_fr.html

#copy update PDF versions of Manual
$HTML2PDF_CMD -q -s A4 -O Portrait ./doc/User_Manual_en.html ./doc/User_Manual_en.pdf
$HTML2PDF_CMD -q -s A4 -O Portrait ./doc/User_Manual_fr.html ./doc/User_Manual_fr.pdf
$HTML2PDF_CMD -q -s A4 -O Portrait ./doc/Developer_Manual_en.html ./doc/Developer_Manual_en.pdf
$HTML2PDF_CMD -q -s A4 -O Portrait ./doc/Developer_Manual_fr.html ./doc/Developer_Manual_fr.pdf

###Update Doxygen_config_file_latex
FILEFOUND=yes
if [ -f Doxygen_config_file_latex ] #does the file exists?
then
  #it exists ==> update
  STRING="PROJECT_NUMBER         = \"v$VERSION\ ($DATE)\"";
  Tempfile=$($MKTEMP_CMD Tempfile_XXXXXXXX);
  $SED_CMD "1,/^PROJECT_NUMBER         =.*/s/^PROJECT_NUMBER         =.*/$STRING/" Doxygen_config_file_latex > "$Tempfile" && mv -f "$Tempfile" Doxygen_config_file_latex;
else
  #it does not exist ==> error
  echo "Error: file Doxygen_config_file_latex does not exist" >&$STDERR ;
  FILEFOUND=no
  exit 1;
fi

###Update Doxygen_style.tex
FILEFOUND=yes
if [ -f Doxygen_style.tex ] #does the file exists?
then
  #it exists ==> update
  STRING="FullSWOF\\\_1D $COMPAT1D";
  Tempfile=$($MKTEMP_CMD Tempfile_XXXXXXXX);
  $SED_CMD "1,/^FullSWOF\\_1D.*/s/^FullSWOF\\\_1D.*/$STRING/" Doxygen_style.tex > "$Tempfile";
  STRING="FullSWOF\\\_2D $COMPAT2D";
  $SED_CMD "1,/^FullSWOF\\_2D.*/s/^FullSWOF\\\_2D.*/$STRING/" "$Tempfile" > Doxygen_style.tex;
  rm -f "$Tempfile";
else
  #it does not exist ==> error
  echo "Error: file Doxygen_style.tex does not exist" >&$STDERR ;
  FILEFOUND=no
  exit 1;
fi

###Update Doxygen_config_file_html
if [ -f Doxygen_config_file_html ] #does the file exists?
then
  #it exists ==> update
  STRING="PROJECT_NUMBER         = \"v$VERSION ($DATE)\"";
  Tempfile=$($MKTEMP_CMD Tempfile_XXXXXXXX);
  $SED_CMD "1,/^PROJECT_NUMBER         =.*/s/^PROJECT_NUMBER         =.*/$STRING/" Doxygen_config_file_html > "$Tempfile" && mv -f "$Tempfile" Doxygen_config_file_html;
else
  #it does not exist ==> error
  echo "Error: file Doxygen_config_file_html does not exist" >&$STDERR ;
  FILEFOUND=no
  exit 1;
fi

###Compile Doxygen
if [[ "$FILEFOUND" == yes ]] #doxygen config files found?
then
  #compilation doxygen LaTeX
  rm -rf doc/latex/
  $DOXYGEN_CMD Doxygen_config_file_latex
  cd doc/latex/ || exit 1
  make
  cp refman.pdf ../
  cd ../../ || exit 1
  rm -rf doc/latex/
  #compilation doxygen html
  rm -rf doc/html/ 
  $DOXYGEN_CMD Doxygen_config_file_html
fi



exit 0
