/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T2t3Package;

/**
 *
 * @author korenciak.marek
 */
public class T2t3Component<K extends Comparable, V extends Object> {

    private T2t3Package.Entry<K, V> LeftComponent;
    private T2t3Package.Entry<K, V> RightComponent;

    private T2t3Component parent;
    private T2t3Component LeftSon;
    private T2t3Component MiddleSon;
    private T2t3Component RightSon;

    public T2t3Component(T2t3Package.Entry<K, V> InitData, T2t3Component initParent) {
        parent = initParent;
        LeftComponent = InitData;
    }

    public T2t3Component(T2t3Package.Entry<K, V> InitData, T2t3Component initParent, T2t3Component initLeftSon, T2t3Component initRightSon) {
        parent = initParent;
        LeftComponent = InitData;
        RightSon = initRightSon;
        LeftSon = initLeftSon;
        if (LeftSon != null) {
            LeftSon.setParent(this);
            RightSon.setParent(this);
        }
    }

    public T2t3Component(T2t3Package.Entry<K, V> initLeftComponent, T2t3Package.Entry<K, V> initRightComponent, T2t3Component initParent, T2t3Component initLeftSon, T2t3Component initMiddleSon, T2t3Component initRightSon) {
        parent = initParent;
        LeftComponent = initLeftComponent;
        RightComponent = initRightComponent;
        RightSon = initRightSon;
        MiddleSon = initMiddleSon;
        LeftSon = initLeftSon;
        if (LeftSon != null) {
            LeftSon.setParent(this);
            RightSon.setParent(this);
        }
        if (MiddleSon != null) {
            MiddleSon.setParent(this);
        }
    }

    public T2t3Component getParent() {
        return parent;
    }

    public void setParent(T2t3Component parentComponent) {
        parent = parentComponent;
    }

    public void setLeftComponent(T2t3Package.Entry<K, V> newLeftComponent) {
        LeftComponent = newLeftComponent;
    }

    public T2t3Package.Entry<K, V> getLeftComponent() {
        return LeftComponent;
    }

    public void setRightComponent(T2t3Package.Entry<K, V> newRightComponent) {
        RightComponent = newRightComponent;
    }

    public void setLeftSon(T2t3Component newLeftSon) {
        LeftSon = newLeftSon;
    }

    public void setRightSon(T2t3Component newRightSon) {
        RightSon = newRightSon;
    }

    public void setMiddleSon(T2t3Component newMiddleSon) {
        MiddleSon = newMiddleSon;
    }

    public T2t3Component getLeftSon() {
        return LeftSon;
    }

    public T2t3Component getRightSon() {
        return RightSon;
    }

    public T2t3Component getMiddleSon() {
        return MiddleSon;
    }

    public T2t3Package.Entry<K, V> getRightComponent() {
        return RightComponent;
    }

    public boolean changeData(T2t3Package.Entry<K, V> localData, T2t3Package.Entry<K, V> newData) {
        if (chackData(LeftComponent, localData)) {
            LeftComponent = newData;
            return true;
        }
        if (chackData(RightComponent, localData)) {
            RightComponent = newData;
            return true;
        }
        return false;
    }

    private boolean chackData(T2t3Package.Entry<K, V> component, T2t3Package.Entry<K, V> localData) {
        if (component != null) {
            if (localData.getKey().compareTo(component.getKey()) == 0) {
                return true;
            }
        }
        return false;
    }

    public short memeberCount() {
        if (LeftComponent != null) {
            if (RightComponent != null) {
                return 2;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    public T2t3Component checkData(K key) {
        if (LeftSon == null && RightSon == null) {
            return this;
        }
        int temp1;
        int temp2;
        if (LeftComponent != null && RightComponent == null) {
            temp1 = key.compareTo(LeftComponent.getKey());
            if (temp1 < 0) {
                return LeftSon;
            }
            if (temp1 == 0) {
                return this;
            }
            if (temp1 > 0) {
                return RightSon;
            }
        }
        if (LeftComponent != null && RightComponent != null) {
            temp1 = key.compareTo(LeftComponent.getKey());
            temp2 = key.compareTo(RightComponent.getKey());
            if (temp1 < 0) {
                return LeftSon;
            }
            if (temp2 > 0) {
                return RightSon;
            }
            if (temp1 > 0 && temp2 < 0) {
                return MiddleSon;
            } else {
                return this;
            }
        }
        return null;
    }

    public T2t3Package.Entry<K, V> getData(K key) {

        if (key.compareTo(LeftComponent.getKey()) == 0) {
            return LeftComponent;
        }

        if (RightComponent != null) {
            if (key.compareTo(RightComponent.getKey()) == 0) {
                return RightComponent;
            }
        }
        return null;
    }

    public boolean add(T2t3Package.Entry<K, V> data) {
        if (LeftComponent != null && data.getKey().compareTo(LeftComponent.getKey()) == 0) {
            throw new ComponentWithKeyExistException("Component with key " + data.toString() + " exist yet.");
        }
        if (RightComponent != null && data.getKey().compareTo(RightComponent.getKey()) == 0) {
            throw new ComponentWithKeyExistException("Component with key " + data.toString() + " exist yet.");
        }
        if (LeftComponent == null) {
            LeftComponent = data;
            return true;
        }
        if (RightComponent == null) {
            if (data.getKey().compareTo(LeftComponent.getKey()) < 0) {
                RightComponent = LeftComponent;
                LeftComponent = data;
                return true;
            }
            RightComponent = data;
            return true;
        }
        return false;
    }

    public boolean deleteChild(T2t3Package.Entry<K, V> data) {
        if (chackComponents(LeftSon, data)) {
            LeftSon = null;
            return true;
        }
        if (chackComponents(MiddleSon, data)) {
            MiddleSon = null;
            return true;
        }
        if (chackComponents(RightSon, data)) {
            RightSon = null;
            return true;
        }
        return false;
    }

    private boolean chackComponents(T2t3Component component, T2t3Package.Entry<K, V> data) {
        if (component != null) {
            if (component.getRightComponent() != null) {
                if (data.getKey().compareTo(component.RightComponent.getKey()) == 0) {
                    return true;
                }
            }
            if (component.getLeftComponent() != null) {
                if (data.getKey().compareTo(component.LeftComponent.getKey()) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean delete(K key) {
        if (LeftSon == null) {
            if ((LeftComponent != null && RightComponent != null) || parent == null) {
                if (RightComponent != null) {
                    if (key.compareTo(RightComponent.getKey()) == 0) {
                        RightComponent = null;
                        return true;
                    }
                }
                if (LeftComponent != null) {
                    if (key.compareTo(LeftComponent.getKey()) == 0) {
                        LeftComponent = RightComponent;
                        RightComponent = null;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
