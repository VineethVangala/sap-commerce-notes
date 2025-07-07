import de.hybris.platform.servicelayer.model.ModelService
import de.hybris.platform.processengine.BusinessProcessService
 
ModelService modelService = spring . getBean(”modelService”)
BusinessProcessService bpService = spring . getBean(”businessProcessService”)
 
MyProcessModel myprocess = bpService . createProcess (”myProcess” +
System . currentTimeMillis () , ”myProcess”)
myprocess . setFail ( true )
myprocess . setError ( true )
 
modelService . save (myprocess)
bpService . startProcess (myprocess)
 
modelService . refresh (myprocess)
println myprocess . state