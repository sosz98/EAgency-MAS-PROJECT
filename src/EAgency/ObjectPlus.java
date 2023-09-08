package EAgency;

import java.io.*;
import java.util.*;

/**
 * The type Object plus.
 */
public class ObjectPlus implements Serializable {
    /**
     * The constant FILE_NAME.
     */
    public static final String FILE_NAME = "objectStorage.dat";
    /**
     * The constant extent.
     */
    public static Map<Class<? extends ObjectPlus>, List> extent = new HashMap<>();

    /**
     * Instantiates a new Object plus.
     */
    public ObjectPlus() {
    }

    /**
     * Save extent.
     *
     * @throws IOException the io exception
     */
    public static void saveExtent() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(extent);
            oos.writeObject(EstateAgent.getMaxProvision());
        }
    }

    /**
     * Load extent.
     *
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static void loadExtent() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            extent = (Map<Class<? extends ObjectPlus>, List>) ois.readObject();
            EstateAgent.setMaxProvision((float) ois.readObject());

        }
    }

    /**
     * Gets extent for class.
     *
     * @param <E>  the type parameter
     * @param clas the clas
     * @return the extent for class
     */
    public static <E> List<E> getExtentForClass(Class<E> clas) {
        return Collections.unmodifiableList(extent.get(clas));
    }

    /**
     * Add to extent.
     */
    protected void addToExtent() {
        List list = extent.computeIfAbsent(this.getClass(), k -> new ArrayList());

        list.add(this);
//        showExtent();
    }

    /**
     * Remove from extent.
     */
    protected void removeFromExtent() {
        List list = extent.get(this.getClass());
        if (list != null) list.remove(this);
    }

//    protected void showExtent() {
//        for (Map.Entry<Class<? extends ObjectPlus> , List> listEntry : extent.entrySet()){
//            System.out.println(listEntry.getKey().getSimpleName());
//            for (Object obj : listEntry.getValue())
//                System.out.println(obj);
//        }
//    }

}
