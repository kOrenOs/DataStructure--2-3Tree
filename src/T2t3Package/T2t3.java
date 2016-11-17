/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T2t3Package;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author korenciak.marek
 */
public class T2t3<K extends Comparable, V extends Object> extends AbstractMap<K, V> {

    private T2t3Component RootParent;

    public T2t3Component get() {
        return RootParent;
    }

    public boolean add(K key, V value) {
        T2t3Package.Entry<K, V> data = new T2t3Package.Entry<K, V>(key, value);
        if (RootParent == null) {
            RootParent = new T2t3Component(data, null);
            return true;
        }

        T2t3Component temp = searchComponent(key);

        try {
            if (!temp.add(data)) {
                temp = findMiddleAndBalance(temp, data);
                while (temp != null) {
                    temp = findMiddleAndBalance(temp.getParent(), temp);
                }
            }
        } catch (ComponentWithKeyExistException | ParentReferenceWrongException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean delete(K key) {
        T2t3Component temp = searchComponent(key);
        if (temp.getData(key) == null) {
            return false;
        }

        if (!temp.delete(key)) {
            T2t3Component temp2;
            if (key.compareTo(temp.getLeftComponent().getKey()) == 0) {
                temp2 = nearestLeafData(temp, false);         //vymenenie vymazavaneho prvku s hodnotovo najblizsim lisotm
            } else {
                temp2 = nearestLeafData(temp, true);
            }

            if (temp2 != null) {
                T2t3Package.Entry<K, V> deletedComponent = new T2t3Package.Entry<K, V>(key, null);
                if (temp2.getRightComponent() != null) {
                    ArrayList<T2t3Package.Entry<K, V>> sortArray = new ArrayList<>();
                    sortArray.add(deletedComponent);
                    sortArray.add(temp2.getLeftComponent());
                    sortArray.add(temp2.getRightComponent());
                    sortArray = sort(sortArray);

                    temp.changeData(deletedComponent, sortArray.get(1));
                    temp2.changeData(sortArray.get(1), deletedComponent);
                } else {
                    temp.changeData(deletedComponent, temp2.getLeftComponent());
                    temp2.changeData(temp2.getLeftComponent(), deletedComponent);
                }
            } else {
                temp2 = temp;
            }
            if (!temp2.delete(key)) {
                temp = findBrotherAndChange(temp2);

                while (temp != null) {
                    temp = fitTree(temp);
                }
            }
        }
        return true;
    }

    private T2t3Component searchComponent(K key) {
        if (RootParent == null) {
            return null;
        }
        T2t3Component temp = RootParent.checkData(key);
        T2t3Component tempBackUp = null;
        while (temp != null && tempBackUp != temp) {
            tempBackUp = temp;
            temp = temp.checkData(key);
        }

        return tempBackUp;
    }

    public T2t3Package.Entry<K, V> searchData(K key) {
        try {
            return searchComponent(key).getData(key);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private T2t3Component findMiddleAndBalance(T2t3Component component, T2t3Component input) {
        if (component.memeberCount() == 2) {
            if (component.deleteChild(input.getLeftComponent())) {
                divide(component, input);
                if (component.getParent() == null) {
                    return null;
                }
                return component;
            } else {
                throw new ParentReferenceWrongException("Wrong parent reference if component with key: "
                        + component.getLeftComponent().toString() + ".");
            }
        } else {
            component.deleteChild(input.getLeftComponent());
            if (component.getLeftSon() == null) {
                component.setRightComponent(component.getLeftComponent());
                component.setLeftComponent(input.getLeftComponent());
                component.setLeftSon(input.getLeftSon());
                component.getLeftSon().setParent(component);
                component.setMiddleSon(input.getRightSon());
                component.getMiddleSon().setParent(component);
            } else {
                component.setRightComponent(input.getLeftComponent());
                component.setRightSon(input.getRightSon());
                component.getRightSon().setParent(component);
                component.setMiddleSon(input.getLeftSon());
                component.getMiddleSon().setParent(component);
            }
        }
        return null;
    }

    private T2t3Component findMiddleAndBalance(T2t3Component component, T2t3Package.Entry<K, V> input) {
        T2t3Package.Entry<K, V> LeftComponent = component.getLeftComponent();
        T2t3Package.Entry<K, V> RightComponent = component.getRightComponent();
        T2t3Package.Entry<K, V> New = input;

        ArrayList<T2t3Package.Entry<K, V>> sortArray = new ArrayList<T2t3Package.Entry<K, V>>();
        sortArray.add(LeftComponent);
        sortArray.add(RightComponent);
        sortArray.add(New);

        sortArray = sort(sortArray);

        component.setLeftSon(new T2t3Component(sortArray.get(0), component));
        component.setRightSon(new T2t3Component(sortArray.get(2), component));
        component.setLeftComponent(sortArray.get(1));
        component.setRightComponent(null);
        if (component.getParent() == null) {
            return null;
        }
        return component;
    }

    private void divide(T2t3Component component, T2t3Component input) {
        if (component.getLeftSon() == null) {
            component.setRightSon(new T2t3Component(component.getRightComponent(),
                    component, component.getMiddleSon(), component.getRightSon()));
            component.setRightComponent(null);
            component.setMiddleSon(null);
            component.setLeftSon(input);
            component.getLeftSon().setParent(component);
            return;
        }
        if (component.getRightSon() == null) { // ak pravy, 
            component.setLeftSon(new T2t3Component(component.getLeftComponent(),
                    component, component.getLeftSon(), component.getMiddleSon()));
            component.setLeftComponent(component.getRightComponent());
            component.setRightComponent(null);
            component.setMiddleSon(null);
            component.setRightSon(input);
            component.getRightSon().setParent(component);
            return;
        }
        if (component.getMiddleSon() == null) {
            component.setLeftSon(new T2t3Component(component.getLeftComponent(),
                    component, component.getLeftSon(), input.getLeftSon()));
            component.setRightSon(new T2t3Component(component.getRightComponent(),
                    component, input.getRightSon(), component.getRightSon()));
            component.setLeftComponent(input.getLeftComponent());
            component.setRightComponent(null);
            component.setMiddleSon(null);
        }
    }

    private T2t3Component nearestLeafData(T2t3Component initComponent, boolean leftSide) {
        if (initComponent.getLeftSon() != null) {
            T2t3Component temp;
            if (leftSide) {
                temp = initComponent.getRightSon();
                while (temp.getLeftSon() != null) {
                    temp = temp.getLeftSon();
                }
                return temp;
            } else {
                temp = initComponent.getLeftSon();
                while (temp.getRightSon() != null) {
                    temp = temp.getRightSon();
                }
                return temp;
            }
        }
        return null;
    }

    private ArrayList sort(ArrayList<T2t3Package.Entry<K, V>> sortArray) {
        T2t3Package.Entry<K, V> temp = null;
        for (int i = 0; i < sortArray.size(); i++) {
            for (int j = sortArray.size() - 1; j > i; j--) {
                if (sortArray.get(j - 1).getKey().compareTo(sortArray.get(j).getKey()) > 0) {
                    temp = sortArray.get(j - 1);
                    sortArray.set(j - 1, sortArray.get(j));
                    sortArray.set(j, temp);
                }
            }
        }
        return sortArray;
    }

//    private ArrayList sort(ArrayList sortArray) {
//        Collections.sort(sortArray);
//        return sortArray;
//    }
    private T2t3Component findBrotherAndChange(T2t3Component component) {
        T2t3Component parent = component.getParent();
        if (parent.getLeftSon() == component) {
            if (parent.memeberCount() == 1) {
                if (parent.getRightSon().memeberCount() == 2) {
                    deleteOneFrom2TreeAnd3Brother(parent, parent.getLeftSon(), parent.getRightSon(), true);
                    return null;
                } else {
                    deleteOneFrom2treeAndSimpleBrother(parent, parent.getRightSon(), true);
                    return parent;
                }
            } else {
                if (parent.getMiddleSon().memeberCount() == 2) {
                    deleteOneFrom3TreeAnd3Brother(parent, parent.getLeftSon(), parent.getMiddleSon(), true);
                    return null;
                } else {
                    deleteOneFrom3TreeAndSimpleBrother(parent, parent.getLeftSon(), parent.getMiddleSon(), true);
                    return null;
                }
            }
        }
        if (parent.getRightSon() == component) {
            if (parent.memeberCount() == 1) {
                if (parent.getLeftSon().memeberCount() == 2) {
                    deleteOneFrom2TreeAnd3Brother(parent, parent.getRightSon(), parent.getLeftSon(), false);
                    return null;
                } else {
                    deleteOneFrom2treeAndSimpleBrother(parent, parent.getLeftSon(), false);
                    return parent;
                }
            } else {
                if (parent.getMiddleSon().memeberCount() == 2) {
                    deleteOneFrom3TreeAnd3Brother(parent, parent.getRightSon(), parent.getMiddleSon(), false);
                    return null;
                } else {
                    deleteOneFrom3TreeAndSimpleBrother(parent, parent.getRightSon(), parent.getMiddleSon(), false);
                    return null;
                }
            }
        }
        if (parent.getMiddleSon() == component) {
            if (parent.getLeftSon().memeberCount() == 2) {
                deleteOneFrom3TreeAnd3Brother(parent, parent.getMiddleSon(), null, true);
                return null;
            }
            if (parent.getRightSon().memeberCount() == 2) {
                deleteOneFrom3TreeAnd3Brother(parent, parent.getMiddleSon(), null, false);
                return null;
            }
            deleteOneFrom3TreeAndSimpleBrother(parent, parent.getMiddleSon(), null, true);
            return null;
        }
        return null;
    }

    private T2t3Component fitTree(T2t3Component temp) {
        T2t3Component parent = temp.getParent();
        if (parent != null) {
            if (parent.memeberCount() == 1) {
                return fitTree2Parent(parent, temp);
            } else {
                fitTree3Parent(parent, temp);
            }
        }
        return null;
    }

    private void deleteOneFrom2treeAndSimpleBrother(T2t3Component parent, T2t3Component brotherLeaf, boolean leftDirection) {
        if (leftDirection) {
            parent.setRightComponent(brotherLeaf.getLeftComponent());
        } else {
            parent.setRightComponent(parent.getLeftComponent());
            parent.setLeftComponent(brotherLeaf.getLeftComponent());
        }
        parent.setLeftSon(null);
        parent.setRightSon(null);
    }

    private void deleteOneFrom2TreeAnd3Brother(T2t3Component parent, T2t3Component deletedLeaf, T2t3Component brotherLeaf, boolean leftDirection) {
        deletedLeaf.setLeftComponent(parent.getLeftComponent());
        if (leftDirection) {
            parent.setLeftComponent(brotherLeaf.getLeftComponent());
            brotherLeaf.setLeftComponent(brotherLeaf.getRightComponent());
        } else {
            parent.setLeftComponent(brotherLeaf.getRightComponent());
        }
        brotherLeaf.setRightComponent(null);
    }

    private void deleteOneFrom3TreeAndSimpleBrother(T2t3Component parent, T2t3Component deletedLeaf, T2t3Component brotherLeaf, boolean leftDirection) {
        if (brotherLeaf != null) {
            if (leftDirection) {
                deletedLeaf.setRightComponent(brotherLeaf.getLeftComponent());
                deletedLeaf.setLeftComponent(parent.getLeftComponent());
                parent.setLeftComponent(parent.getRightComponent());
            } else {
                deletedLeaf.setLeftComponent(brotherLeaf.getLeftComponent());
                deletedLeaf.setRightComponent(parent.getRightComponent());
            }
        } else {
            brotherLeaf = parent.getRightSon();
            brotherLeaf.setRightComponent(brotherLeaf.getLeftComponent());
            brotherLeaf.setLeftComponent(parent.getRightComponent());
        }
        parent.setRightComponent(null);
        parent.setMiddleSon(null);
    }

    private void deleteOneFrom3TreeAnd3Brother(T2t3Component parent, T2t3Component deletedLeaf, T2t3Component brotherLeaf, boolean leftDirection) {
        if (brotherLeaf != null) {
            if (leftDirection) {
                deletedLeaf.setLeftComponent(parent.getLeftComponent());
                parent.setLeftComponent(brotherLeaf.getLeftComponent());
                brotherLeaf.setLeftComponent(brotherLeaf.getRightComponent());
            } else {
                deletedLeaf.setLeftComponent(parent.getRightComponent());
                parent.setRightComponent(brotherLeaf.getRightComponent());
            }
            brotherLeaf.setRightComponent(null);
        } else {
            if (leftDirection) {
                brotherLeaf = parent.getLeftSon();
                deletedLeaf.setLeftComponent(parent.getLeftComponent());
                parent.setLeftComponent(brotherLeaf.getRightComponent());
                brotherLeaf.setRightComponent(null);
            } else {
                brotherLeaf = parent.getRightSon();
                deletedLeaf.setLeftComponent(parent.getRightComponent());
                parent.setRightComponent(brotherLeaf.getLeftComponent());
                brotherLeaf.setLeftComponent(brotherLeaf.getRightComponent());
                brotherLeaf.setRightComponent(null);
            }
        }
    }

    private T2t3Component fitTree2Parent(T2t3Component parent, T2t3Component unfittedComponent) {
        T2t3Component brother;
        if (parent.getLeftSon() == unfittedComponent) {
            brother = parent.getRightSon();
            if (brother.memeberCount() == 2) {
                unfittedComponent.setLeftSon(new T2t3Component(unfittedComponent.getLeftComponent(), unfittedComponent.getRightComponent(),
                        unfittedComponent, unfittedComponent.getLeftSon(), unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
                unfittedComponent.setLeftComponent(parent.getLeftComponent());
                unfittedComponent.setRightSon(brother.getLeftSon());
                unfittedComponent.getRightSon().setParent(unfittedComponent);
                parent.setLeftComponent(brother.getLeftComponent());
                brother.setLeftComponent(brother.getRightComponent());
                brother.setRightComponent(null);
                brother.setLeftSon(brother.getMiddleSon());
                brother.setMiddleSon(null);
                unfittedComponent.setMiddleSon(null);
                unfittedComponent.setRightComponent(null);
                parent.setRightComponent(null);
            } else {
                parent.setRightComponent(brother.getLeftComponent());
                parent.setMiddleSon(brother.getLeftSon());
                parent.getMiddleSon().setParent(parent);
                parent.setRightSon(brother.getRightSon());
                parent.getRightSon().setParent(parent);
                return parent;
            }
        } else {
            brother = parent.getLeftSon();
            if (brother.memeberCount() == 2) {
                unfittedComponent.setRightSon(new T2t3Component(unfittedComponent.getLeftComponent(), unfittedComponent.getRightComponent(),
                        unfittedComponent, unfittedComponent.getLeftSon(), unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
                unfittedComponent.setLeftComponent(parent.getLeftComponent());
                unfittedComponent.setLeftSon(brother.getRightSon());
                unfittedComponent.getLeftSon().setParent(unfittedComponent);
                parent.setLeftComponent(brother.getRightComponent());
                brother.setRightSon(brother.getMiddleSon());
                brother.setMiddleSon(null);
                unfittedComponent.setMiddleSon(null);
                unfittedComponent.setRightComponent(null);
                brother.setRightComponent(null);
                parent.setRightComponent(null);
            } else {
                parent.setRightComponent(parent.getLeftComponent());
                parent.setLeftComponent(brother.getLeftComponent());
                parent.setLeftSon(brother.getLeftSon());
                parent.getLeftSon().setParent(parent);
                parent.setMiddleSon(brother.getRightSon());
                parent.getMiddleSon().setParent(parent);
                return parent;
            }
        }
        return null;
    }

    private void fitTree3Parent(T2t3Component parent, T2t3Component unfittedComponent) {
        if (parent.getLeftSon() == unfittedComponent) {
            if (parent.getMiddleSon().memeberCount() == 2) {
                fitTree3Parent3Brother(parent, unfittedComponent, parent.getMiddleSon(), true);
            } else {
                fitTree3ParentSimpleBrother(parent, unfittedComponent, parent.getMiddleSon(), true);
            }
        }
        if (parent.getRightSon() == unfittedComponent) {
            if (parent.getMiddleSon().memeberCount() == 2) {
                fitTree3Parent3Brother(parent, unfittedComponent, parent.getMiddleSon(), false);
            } else {
                fitTree3ParentSimpleBrother(parent, unfittedComponent, parent.getMiddleSon(), false);
            }
        }
        if (parent.getMiddleSon() == unfittedComponent) {
            if (parent.getLeftSon().memeberCount() == 2 || parent.getRightSon().memeberCount() == 2) {
                if (parent.getLeftSon().memeberCount() == 2) {
                    fitTree3Parent3Brother(parent, unfittedComponent, null, true);
                    return;
                } else {
                    fitTree3Parent3Brother(parent, unfittedComponent, null, false);
                }
            } else {
                fitTree3ParentSimpleBrother(parent, parent.getMiddleSon(), null, false);
            }
        }
    }

    private void fitTree3ParentSimpleBrother(T2t3Component parent, T2t3Component unfittedComponent, T2t3Component brother, boolean leftDirection) {
        if (brother != null) {
            if (leftDirection) {
                unfittedComponent.setLeftSon(new T2t3Component(unfittedComponent.getLeftComponent(), unfittedComponent.getRightComponent(),
                        unfittedComponent, unfittedComponent.getLeftSon(), unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
                unfittedComponent.setLeftComponent(parent.getLeftComponent());
                unfittedComponent.setRightComponent(brother.getLeftComponent());
                unfittedComponent.setMiddleSon(brother.getLeftSon());
                unfittedComponent.getMiddleSon().setParent(unfittedComponent);
                unfittedComponent.setRightSon(brother.getRightSon());
                unfittedComponent.getRightSon().setParent(unfittedComponent);
                parent.setLeftComponent(parent.getRightComponent());
            } else {
                unfittedComponent.setRightSon(new T2t3Component(unfittedComponent.getLeftComponent(), unfittedComponent.getRightComponent(),
                        unfittedComponent, unfittedComponent.getLeftSon(), unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
                unfittedComponent.setRightComponent(parent.getRightComponent());
                unfittedComponent.setLeftComponent(brother.getLeftComponent());
                unfittedComponent.setMiddleSon(brother.getRightSon());
                unfittedComponent.getMiddleSon().setParent(unfittedComponent);
                unfittedComponent.setLeftSon(brother.getLeftSon());
                unfittedComponent.getLeftSon().setParent(unfittedComponent);
            }
        } else {
            T2t3Component leftBrother = parent.getLeftSon();
            T2t3Component rightBrother = parent.getRightSon();

            if (leftBrother.getRightSon() != null) {
                if (leftBrother.getRightSon().memeberCount() == 2) {
                    leftBrother.setRightComponent(leftBrother.getRightSon().getRightComponent());
                    leftBrother.getRightSon().setRightComponent(null);
                    leftBrother.setMiddleSon(leftBrother.getRightSon());
                    leftBrother.setRightSon(new T2t3Component(parent.getLeftComponent(), leftBrother,
                            leftBrother.getMiddleSon().getRightSon(), unfittedComponent.getLeftSon()));
                    leftBrother.getMiddleSon().setRightSon(leftBrother.getMiddleSon().getMiddleSon());
                    leftBrother.getMiddleSon().setMiddleSon(null);
                    parent.setLeftComponent(unfittedComponent.getLeftComponent());
                    rightBrother.setMiddleSon(rightBrother.getLeftSon());
                    rightBrother.setLeftSon(new T2t3Component(unfittedComponent.getRightComponent(), rightBrother,
                            unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
                    rightBrother.setRightComponent(rightBrother.getLeftComponent());
                    rightBrother.setLeftComponent(parent.getRightComponent());
                    parent.setRightComponent(null);
                    parent.setMiddleSon(null);
                    return;
                }
            }

            if (unfittedComponent.getLeftSon() != null) {
                leftBrother.getRightSon().setMiddleSon(leftBrother.getRightSon().getRightSon());
                leftBrother.getRightSon().setRightSon(unfittedComponent.getLeftSon());
                leftBrother.getRightSon().getRightSon().setParent(leftBrother.getRightSon());
            }
            leftBrother.getRightSon().setRightComponent(parent.getLeftComponent());
            parent.setLeftComponent(unfittedComponent.getLeftComponent());
            rightBrother.setRightComponent(rightBrother.getLeftComponent());
            rightBrother.setLeftComponent(parent.getRightComponent());
            rightBrother.setMiddleSon(rightBrother.getLeftSon());
            rightBrother.setLeftSon(new T2t3Component(unfittedComponent.getRightComponent(), rightBrother,
                    unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
        }
        parent.setRightComponent(null);
        parent.setMiddleSon(null);
    }

    private void fitTree3Parent3Brother(T2t3Component parent, T2t3Component unfittedComponent, T2t3Component brother, boolean leftDirection) {
        if (brother == null) {
            if (leftDirection) {
                brother = parent.getLeftSon();
                unfittedComponent.setRightSon(new T2t3Component(unfittedComponent.getLeftComponent(), unfittedComponent.getRightComponent(),
                        unfittedComponent, unfittedComponent.getLeftSon(), unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
                unfittedComponent.setLeftComponent(parent.getLeftComponent());
                unfittedComponent.setLeftSon(brother.getRightSon());
                unfittedComponent.getLeftSon().setParent(unfittedComponent);
                parent.setLeftComponent(brother.getRightComponent());
                brother.setRightSon(brother.getMiddleSon());
                brother.setMiddleSon(null);
                brother.setRightComponent(null);
            } else {
                brother = parent.getRightSon();
                unfittedComponent.setLeftSon(new T2t3Component(unfittedComponent.getLeftComponent(), unfittedComponent.getRightComponent(),
                        unfittedComponent, unfittedComponent.getLeftSon(), unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
                unfittedComponent.setLeftComponent(parent.getRightComponent());
                unfittedComponent.setRightSon(brother.getLeftSon());
                unfittedComponent.getRightSon().setParent(unfittedComponent);
                parent.setRightComponent(brother.getLeftComponent());
                brother.setLeftComponent(brother.getRightComponent());
                brother.setLeftSon(brother.getMiddleSon());
                brother.setMiddleSon(null);
                brother.setRightComponent(null);
            }
        } else {
            if (leftDirection) {
                unfittedComponent.setLeftSon(new T2t3Component(unfittedComponent.getLeftComponent(), unfittedComponent.getRightComponent(),
                        unfittedComponent, unfittedComponent.getLeftSon(), unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
                unfittedComponent.setLeftComponent(parent.getLeftComponent());
                unfittedComponent.setRightSon(brother.getLeftSon());
                unfittedComponent.getRightSon().setParent(unfittedComponent);
                parent.setLeftComponent(brother.getLeftComponent());
                brother.setLeftComponent(brother.getRightComponent());
                brother.setLeftSon(brother.getMiddleSon());
            } else {
                unfittedComponent.setRightSon(new T2t3Component(unfittedComponent.getLeftComponent(), unfittedComponent.getRightComponent(),
                        unfittedComponent, unfittedComponent.getLeftSon(), unfittedComponent.getMiddleSon(), unfittedComponent.getRightSon()));
                unfittedComponent.setLeftComponent(parent.getRightComponent());
                unfittedComponent.setLeftSon(brother.getRightSon());
                unfittedComponent.getLeftSon().setParent(unfittedComponent);
                parent.setRightComponent(brother.getRightComponent());
                brother.setRightSon(brother.getMiddleSon());
            }
            brother.setRightComponent(null);
            brother.setMiddleSon(null);
        }
        unfittedComponent.setRightComponent(null);
        unfittedComponent.setMiddleSon(null);
    }

    public ArrayList<V> inOrder() {
        T2t3Component<K,V> temp = mostLeft(RootParent, true);
        T2t3Component<K,V> mostRightComponent = mostLeft(RootParent, false);
        ArrayList<V> inOrderArray = new ArrayList<>();
        ArrayList<K> keyArray = new ArrayList<>();

        while (temp != mostRightComponent) {
            if (temp.getLeftSon() == null) {                
                inOrderArray.add(temp.getLeftComponent().getValue());
                keyArray.add(temp.getLeftComponent().getKey());
                if (temp.getRightComponent() != null) {
                    inOrderArray.add(temp.getRightComponent().getValue());
                    keyArray.add(temp.getRightComponent().getKey());
                }
                temp = temp.getParent();
            } else {
                if (temp.memeberCount() == 2) {                    
                    if(keyArray.get(inOrderArray.size() - 1).compareTo(temp.getLeftComponent().getKey()) < 0){
                        inOrderArray.add(temp.getLeftComponent().getValue());
                        keyArray.add(temp.getLeftComponent().getKey());
                        temp = mostLeft(temp.getMiddleSon(),true);
                        continue;
                    }
                    if(keyArray.get(inOrderArray.size() - 1).compareTo(temp.getLeftComponent().getKey()) > 0 &&
                            keyArray.get(inOrderArray.size() - 1).compareTo(temp.getRightComponent().getKey()) < 0){
                        inOrderArray.add(temp.getRightComponent().getValue());
                        keyArray.add(temp.getRightComponent().getKey());
                        temp = mostLeft(temp.getRightSon(), true);
                        continue;
                    }
                    if(keyArray.get(inOrderArray.size() - 1).compareTo(temp.getLeftComponent().getKey()) > 0 &&
                            keyArray.get(inOrderArray.size() - 1).compareTo(temp.getRightComponent().getKey()) > 0){
                        temp = temp.getParent();
                    }                    
                }else{
                    if(keyArray.get(inOrderArray.size() - 1).compareTo(temp.getLeftComponent().getKey()) < 0){
                        inOrderArray.add(temp.getLeftComponent().getValue());
                        keyArray.add(temp.getLeftComponent().getKey());
                        temp = mostLeft(temp.getRightSon(),true);
                        continue;
                    }
                    if(keyArray.get(inOrderArray.size() - 1).compareTo(temp.getLeftComponent().getKey()) > 0){
                        temp = temp.getParent();
                    }
                }
            }
        }
        inOrderArray.add(temp.getLeftComponent().getValue());
        if(temp.memeberCount()==2){
            inOrderArray.add(temp.getRightComponent().getValue());
        }
        return inOrderArray;
    }

    private T2t3Component mostLeft(T2t3Component startComponent, boolean leftDirection) {
        T2t3Component temp = startComponent;
        if (leftDirection) {
            while (temp.getLeftSon() != null) {
                temp = temp.getLeftSon();
            }
        } else {
            while (temp.getRightSon() != null) {
                temp = temp.getRightSon();
            }
        }
        return temp;
    }

    public int componentsInto() {
        int count = 0;
        HashSet<K> chackSet = new HashSet<>();
        ArrayList<T2t3Component> components = new ArrayList<>();
        int leafDistance = 0;
        boolean leafDistControl = true;
        boolean okStructure = true;
        T2t3Component<K, V> temp;

        components.add(RootParent);

        while (!components.isEmpty()) {
            temp = components.get(0);
            if (temp == null) {
                break;
            }

            if (components.get(0).memeberCount() == 0 && components.get(0).getLeftSon() == null) {
                return 0;
            }
            count += components.get(0).memeberCount();
            if (components.get(0).memeberCount() == 2) {
                if (!chackSet.add(temp.getRightComponent().getKey())) {
                    System.out.println("duplicate key: " + temp.getRightComponent().getKey());
                }
                if (components.get(0).getLeftSon() != null) {
                    T2t3Component<K, V> leftSon = temp.getLeftSon();
                    T2t3Component<K, V> middleSon = temp.getMiddleSon();
                    T2t3Component<K, V> rightSon = temp.getRightSon();

                    if (!(leftSon.getLeftComponent().getKey().compareTo(temp.getLeftComponent().getKey()) < 0
                            && middleSon.getLeftComponent().getKey().compareTo(temp.getLeftComponent().getKey()) > 0
                            && middleSon.getLeftComponent().getKey().compareTo(temp.getRightComponent().getKey()) < 0
                            && rightSon.getLeftComponent().getKey().compareTo(temp.getRightComponent().getKey()) > 0
                            && temp.getLeftComponent().getKey().compareTo(temp.getRightComponent().getKey()) < 0)) {
                        okStructure = false;
                        throw new NullPointerException();
                    }
                }
            }
            try {
                if (!chackSet.add(temp.getLeftComponent().getKey())) {
                    System.out.println("duplicate key: " + components.get(0).getRightComponent().getKey());
                    throw new NullPointerException();
                }
            } catch (NullPointerException e) {
                System.out.println("left component not found");
                throw new NullPointerException();
            }

            if (components.get(0).getLeftSon() != null) {
                components.add(components.get(0).getLeftSon());
                components.add(components.get(0).getRightSon());
            }
            if (components.get(0).getMiddleSon() != null) {
                components.add(components.get(0).getMiddleSon());
            }

            if (components.get(0).getLeftSon() == null) {
                T2t3Component<K, V> temp2 = components.get(0);
                if (leafDistance == 0) {
                    leafDistance = componentDistance(temp2.getLeftComponent().getKey());
                } else {
                    if (leafDistance != componentDistance(temp2.getLeftComponent().getKey())) {
                        System.out.println("abnormal leaf distance!!!");
                        throw new NullPointerException();
                    }
                }
            }
            components.remove(0);
        }
        System.out.println("Data key structure ok?  " + okStructure);
        System.out.println("Leaf distance structure ok?  " + leafDistControl);
        return chackSet.size();
    }

    public int componentDistance(K key) {
        int distance = 0;
        T2t3Component temp = RootParent.checkData(key);
        T2t3Component tempBackUp = null;
        while (temp != null && tempBackUp != temp) {
            tempBackUp = temp;
            temp = temp.checkData(key);
            distance++;
        }
        return distance;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
