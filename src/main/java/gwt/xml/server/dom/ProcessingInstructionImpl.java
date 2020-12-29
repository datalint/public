package gwt.xml.server.dom;

import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;

public class ProcessingInstructionImpl extends NodeImpl implements ProcessingInstruction {
	private final String target;
	private String data;

	public ProcessingInstructionImpl(Node owner, String target, String data) {
		super(owner);

		this.target = target;
		this.data = data;
	}

	@Override
	public String getNodeName() {
		return target;
	}

	@Override
	public String getNodeValue() {
		return data;
	}

	@Override
	public short getNodeType() {
		return PROCESSING_INSTRUCTION_NODE;
	}

	@Override
	public Node cloneNode(boolean deep) {
		return new ProcessingInstructionImpl(getOwnerDocument(), target, data);
	}

	@Override
	public String getTarget() {
		return getNodeName();
	}

	@Override
	public String getData() {
		return getNodeValue();
	}

	@Override
	public void setData(String data) {
		this.data = data;
	}
}
