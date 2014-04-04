package nl.bartpelle.jgb;

/**
 * 
 * The main engine behind the emulator: the central processing unit.
 * 
 * @author Bart Pelle
 *
 */
public class GBZ80 {

	/*
	 * REGISTERS
	 */
	
	/**
	 * Accumulator register, mainly used for results from arithmetic/logical operations.
	 */
	private byte A;
	
	/**
	 * Auxiliary register, high byte of combined 16-bit register 'BC'.
	 */
	private byte B;
	
	/**
	 * Auxiliary register, low byte of combined 16-bit register 'BC'.
	 */
	private byte C;
	
	/**
	 * Auxiliary register, high byte of combined 16-bit register 'DE'.
	 */
	private byte D;
	
	/**
	 * Auxiliary register, low byte of combined 16-bit register 'DE'.
	 */
	private byte E;
	
	/**
	 * Flags register, holds flags for arithmetic and logical operations (e.g. carry).
	 */
	private byte F;
	
	/**
	 * Auxiliary register mostly used in address storing. High byte of combined 16-bit register 'HL'.
	 */
	private byte H;
	
	/**
	 * Auxiliary register mostly used in address storing. Low byte of combined 16-bit register 'HL'.
	 */
	private byte L;
	
	/**
	 * The currently used ROM bank. A value of 0 or 1 indicates bank 1, values 2 to 7 indicate bank 2 to 7.
	 */
	private byte romBank;
	
	/**
	 * The actual game ROM bytes that contain the instructions.
	 */
	private byte[] rom;
	
	/**
	 * The stack of the processor. Consists of 65535 bytes.
	 */
	private byte[] stack = new byte[0xFFFF];
	
	/**
	 * The cartridge that is currently running.
	 */
	private GameCartridge cartridge;
	
	/**
	 * The 'program counter', which resembles the offset on the ROM where we will execute our instruction.
	 */
	private int pc;
	
	/**
	 * The 'stack pointer', which resembles the position in the stack. Initially set to the top of the stack.
	 * Decreases when something is pushed and increases when something is popped.
	 */
	private int sp = 0xFFFE;
	
	/**
	 * Create a new Z80 processor emulator from the specified {@link GameCartridge}.
	 * @param cartridge the cartridge which contains the game ROM and various other information.
	 */
	public GBZ80(GameCartridge cartridge) {
		this.cartridge = cartridge;
		rom = cartridge.rom;
		pc = cartridge.startAddress;
	}
	
	/**
	 * Process a specified amount of instructions to let the processor interpret.
	 * @param amount the number of instructions to execute, or <code>-1</code> to run indefinitely.
	 */
	public void processInstructions(int amount) {
		int instr = -1;
		int cycles; // Amount of consumed cycles (afaik this is used internally somewhere)
		int opcode;
		
		while (++instr != amount) {
			// And so... it begins.
			opcode = rom[pc] & 0xFF;
			
			switch (opcode) {
			case Instructions.LD_A_A:
				// A <- A obviously does nothing
				cycles = 1;
				pc++;
				break;
			case Instructions.LD_B_A:
				B = A;
				cycles = 1;
				pc++;
				break;
			case Instructions.LD_C_A:
				C = A;
				cycles = 1;
				pc++;
				break;
			case Instructions.LD_D_A:
				D = A;
				cycles = 1;
				pc++;
				break;
			case Instructions.LD_E_A:
				E = A;
				cycles = 1;
				pc++;
				break;
			case Instructions.LD_H_A:
				H = A;
				cycles = 1;
				pc++;
				break;
			case Instructions.LD_L_A:
				L = A;
				cycles = 1;
				pc++;
				break;
			default:
				System.err.println("Unknown Z80 instruction: $" + Integer.toHexString(opcode).toUpperCase() + ".");
				return;
			}
		}
	}
	
}
