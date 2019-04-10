package dronePackage;

public class IteratorClass implements Iterator {
	ManagedObject[] i_objects;
	/**
	 * Length of array, starting at 1
	 */
	int i_length;
	int i_position;

	public IteratorClass() {
		ManagedObject[] l_objects = new ManagedObject[1];
		i_objects = l_objects;
		i_length = 0;
		i_position = 0;
	}

	public int length() {
		return i_length;
	}

	public boolean IdExists(String a_Identifier) {
		return getElement(a_Identifier) != null;
	}

	@Override
	public boolean hasNext() {
		return i_position < i_length;
	}

	@Override
	public ManagedObject next() {
		return i_objects[i_position++];
	}

	@Override
	public void reset() {
		i_position = 0;
	}

	public void add(ManagedObject a_toAdd) {

		if (i_length >= i_objects.length) {
			ManagedObject[] l_new = new ManagedObject[i_objects.length * 2];
			for (int i = 0; i < i_length; i++) {
				l_new[i] = i_objects[i];
			}
			i_objects = l_new;
		}
		i_objects[i_length++] = a_toAdd;
	}

	public ManagedObject getElement(String a_Identifier) {
		ManagedObject l_Object;
		reset();
		while (hasNext()) {
			l_Object = next();
			if (l_Object.getObjectID().equals(a_Identifier))
				return l_Object; // object was found
		}
		return null;

	}

	public int addElement(ManagedObject a_Object) {
		ManagedObject l_Object = getElement(a_Object.getObjectID());
		if (l_Object == null) {
			add(a_Object);
			return 1;
		} else
			return 0;
	}

	public boolean removeElement(String a_id) {
		boolean l_exists = false;
		int l_objIndex = -1;
		for (int i = 0; i < i_length; i++) {
			if (i_objects[i].getObjectID().equals(a_id)) {
				l_exists = true;
				l_objIndex = i;
				break;
			}
		}
		if (l_exists) {
			for (int i = l_objIndex + 1; i < i_length; i++) {
				i_objects[i-1]=i_objects[i];
			}
			i_length--;
		}
		reset();
		return l_exists;
	}

	public boolean moveTo(String a_itemId,Iterator a_iterator){
		a_iterator.addElement(getElement(a_itemId));
		return removeElement(a_itemId);
	}

}