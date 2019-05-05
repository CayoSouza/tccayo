/*
 * Created on 04.04.2014
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pfsw.tools.cda.base.model.ClassInformation;
import org.pfsw.tools.cda.base.model.processing.IClassInformationProcessor;

public class ClassNameFinder implements IClassInformationProcessor<Integer>
{
  private Integer counter = 0;
  private Pattern pattern;
  
  public ClassNameFinder(Pattern pattern)
  {
    super();
    this.pattern = pattern;
  }
  
  @Override
  public boolean process(ClassInformation element)
  {
    counter++;
    return true;
  }
  
  @Override
  public boolean matches(ClassInformation element)
  {
    if (this.pattern == null)
    {
      return false;
    }
    Matcher matcher = this.pattern.matcher(element.getJustClassName());
    return matcher.matches();
  }
  
  @Override
  public Integer getResultData()
  {
    return counter;
  }
}
