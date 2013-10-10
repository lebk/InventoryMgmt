package com.leikai.vmguest;

import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import java.rmi.RemoteException;

public class GuestProcessDirector
{

	VirtualMachine vm;
	GuestProcessManager gpm;
	NamePasswordAuthentication auth;

	public GuestProcessDirector(VirtualMachine vm, String user, String password)
	{
		auth = new NamePasswordAuthentication();
		this.vm = vm;
		gpm = vm.getServerConnection().getServiceInstance().getGuestOperationsManager().getProcessManager(vm);
		auth.username = user;
		auth.password = password;
	}

	public long run(String programPath, String arguments)
		throws GuestOperationsFault, InvalidState, TaskInProgress, FileFault, RuntimeFault, RemoteException
	{
		GuestProgramSpec spec = new GuestProgramSpec();
		spec.programPath = programPath;
		spec.arguments = arguments;
		return gpm.startProgramInGuest(auth, spec);
	}

	public long run(String scriptPath)
		throws GuestOperationsFault, InvalidState, TaskInProgress, FileFault, RuntimeFault, RemoteException
	{
		String prog;
		String args;
		if (scriptPath.startsWith("\""))
		{
			int next = scriptPath.indexOf("\"", 1);
			prog = scriptPath.substring(1, next);
			args = scriptPath.substring(next + 1);
		} else
		{
			int next = scriptPath.indexOf(" ");
			if (next == -1)
			{
				prog = scriptPath;
				args = "";
			} else
			{
				prog = scriptPath.substring(0, next);
				args = scriptPath.substring(next + 1);
			}
		}
		return run(prog, args);
	}

	public GuestProcessInfo[] listProcesses()
		throws GuestOperationsFault, InvalidState, TaskInProgress, RuntimeFault, RemoteException
	{
		return listProcesses(null);
	}

	public GuestProcessInfo[] listProcesses(long pids[])
		throws GuestOperationsFault, InvalidState, TaskInProgress, RuntimeFault, RemoteException
	{
		return gpm.listProcessesInGuest(auth, pids);
	}

	public void killProcess(long pid)
		throws GuestOperationsFault, InvalidState, TaskInProgress, RuntimeFault, RemoteException
	{
		gpm.terminateProcessInGuest(auth, pid);
	}

	public String[] readEnvironmentVariables(String names[])
		throws GuestOperationsFault, InvalidState, TaskInProgress, RuntimeFault, RemoteException
	{
		return gpm.readEnvironmentVariableInGuest(auth, names);
	}

	public String[] readEnvironmentVariables()
		throws GuestOperationsFault, InvalidState, TaskInProgress, RuntimeFault, RemoteException
	{
		return readEnvironmentVariables(null);
	}
}
