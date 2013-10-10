package com.leikai.util;


import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.VirtualMachineSnapshotInfo;
import com.vmware.vim25.VirtualMachineSnapshotTree;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualMachineSnapshot;


public class VMSnapshotManagement {

	public static void createSnapshot(VirtualMachine vm,String Snapshotname, String SnapshotDesc) throws Exception {
		Task task = vm.createSnapshot_Task(Snapshotname, SnapshotDesc, false,false);
		if (task.waitForTask() == Task.SUCCESS) {
			System.out.println("Basic Snapshot is created...");
		}
	}

	public static void listSnapshot(VirtualMachine vm) throws Exception {
		if (vm == null) {
			return;
		}
		VirtualMachineSnapshotInfo snapInfo = vm.getSnapshot();
		VirtualMachineSnapshotTree[] snapTree = snapInfo.getRootSnapshotList();
		printSnapshots(snapTree);
	}

	static void printSnapshots(VirtualMachineSnapshotTree[] snapTree)
			throws Exception {
		for (int i = 0; snapTree != null && i < snapTree.length; i++) {
			VirtualMachineSnapshotTree node = snapTree[i];
			System.out.println("Snapshot Name : " + node.getName());
			VirtualMachineSnapshotTree[] childTree = node
					.getChildSnapshotList();
			if (childTree != null) {
				printSnapshots(childTree);
			}
		}
	}

	static VirtualMachineSnapshot getSnapshotInTree(VirtualMachine vm,String snapName) {
		if (vm == null || snapName == null) {
			return null;
		}
		VirtualMachineSnapshotTree[] snapTree = vm.getSnapshot()
				.getRootSnapshotList();
		if (snapTree != null) {
			ManagedObjectReference mor = findSnapshotInTree(snapTree, snapName);
			if (mor != null) {
				return new VirtualMachineSnapshot(vm.getServerConnection(), mor);
			}
		}
		return null;
	}

	static ManagedObjectReference findSnapshotInTree(VirtualMachineSnapshotTree[] snapTree, String snapName) {
		for (int i = 0; i < snapTree.length; i++) {
			VirtualMachineSnapshotTree node = snapTree[i];
			if (snapName.equals(node.getName())) {
				return node.getSnapshot();
			} else {
				VirtualMachineSnapshotTree[] childTree = node
						.getChildSnapshotList();
				if (childTree != null) {
					ManagedObjectReference mor = findSnapshotInTree(childTree,
							snapName);
					if (mor != null) {
						return mor;
					}
				}
			}
		}
		return null;
	}

	public static void revertSnapshot(VirtualMachine vm, String snapshotname)throws Exception {
		VirtualMachineSnapshot vmsnap = getSnapshotInTree(vm, snapshotname);
		if (vmsnap != null) {
			Task task = vmsnap.revertToSnapshot_Task(null);
			if (task.waitForTask() == Task.SUCCESS) {
				System.out.println("Reverted to snapshot:" + snapshotname);
			}
		}
	}

	public static void removeSnapshot(VirtualMachine vm, String snapshotname,boolean removechild) throws Exception {
		VirtualMachineSnapshot vmsnap = getSnapshotInTree(vm, snapshotname);
		if (vmsnap != null) {
			Task task = vmsnap.removeSnapshot_Task(removechild);
			if (task.waitForTask() == Task.SUCCESS) {
				System.out.println("Removed snapshot:" + snapshotname);
			}
		}
	}
	
	
	
}

